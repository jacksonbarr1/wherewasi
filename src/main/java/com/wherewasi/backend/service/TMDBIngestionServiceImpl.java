package com.wherewasi.backend.service;

import com.wherewasi.backend.client.TMDBApiClient;
import com.wherewasi.backend.dto.tmdb.TMDBEpisodeDTO;
import com.wherewasi.backend.dto.tmdb.TMDBShowDTO;
import com.wherewasi.backend.dto.tmdb.TMDBShowIdExportDTO;
import com.wherewasi.backend.entity.*;
import com.wherewasi.backend.mapper.*;
import com.wherewasi.backend.repository.*;
import com.wherewasi.backend.util.StringUtil;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TMDBIngestionServiceImpl implements TMDBIngestionService {

    // TODO: Use generics to decouple processing logic from specific nested DTO types

    private static final Logger logger = LoggerFactory.getLogger(TMDBIngestionServiceImpl.class);

    private final ShowRepository showRepository;
    private final GenreRepository genreRepository;
    private final CreatorRepository creatorRepository;
    private final NetworkRepository networkRepository;
    private final SeasonRepository seasonRepository;
    private final EpisodeRepository episodeRepository;
    private final TMDBApiClient tmdbApiClient;
    private final ShowMapper showMapper;
    private final GenreMapper genreMapper;
    private final CreatorMapper creatorMapper;
    private final NetworkMapper networkMapper;
    private final SeasonMapper seasonMapper;
    private final EpisodeMapper episodeMapper;

    public TMDBIngestionServiceImpl(ShowRepository showRepository, GenreRepository genreRepository,
                                    CreatorRepository creatorRepository, NetworkRepository networkRepository,
                                    SeasonRepository seasonRepository, EpisodeRepository episodeRepository,
                                    TMDBApiClient tmdbApiClient, ShowMapper showMapper, GenreMapper genreMapper,
                                    CreatorMapper creatorMapper, NetworkMapper networkMapper, SeasonMapper seasonMapper,
                                    EpisodeMapper episodeMapper) {
        this.showRepository = showRepository;
        this.genreRepository = genreRepository;
        this.creatorRepository = creatorRepository;
        this.networkRepository = networkRepository;
        this.seasonRepository = seasonRepository;
        this.episodeRepository = episodeRepository;
        this.tmdbApiClient = tmdbApiClient;
        this.showMapper = showMapper;
        this.genreMapper = genreMapper;
        this.creatorMapper = creatorMapper;
        this.networkMapper = networkMapper;
        this.seasonMapper = seasonMapper;
        this.episodeMapper = episodeMapper;
    }


    @Override
    @Scheduled(cron = "0 53 13 * * *", zone="America/New_York")
    public void dailyIngestionJob() {
        logger.info("Starting daily TMDB ingestion job.");
        try {
            Stream<TMDBShowIdExportDTO> processedStream = processDatedIdFile(LocalDate.now
                    (ZoneId.of("America/New_York")));

            processedStream.forEach(tmdbShowIdExportDTO -> {
                try {
                    processShow(tmdbShowIdExportDTO.getId());
                } catch (Exception e) {
                    logger.error("Error processing show with ID {}: {}", tmdbShowIdExportDTO.getId(), e.getMessage(), e);
                }
            });
            logger.info("Daily TMDB ingestion job completed successfully.");
        } catch (Exception e) {
            logger.error("Daily TMDB ingestion job failed: {}", e.getMessage(), e);
        }
    }

    private Stream<TMDBShowIdExportDTO> processDatedIdFile(LocalDate date) {
        logger.info("Attempting to retrieve show IDs for date: {}", date);
        try {
            Stream<TMDBShowIdExportDTO> showIdStream = tmdbApiClient.downloadAndStreamShowIds(date);
            logger.info("Successfully retrieved show IDs for date: {}", date);

            logger.info("Filtering and sorting retrieved show IDs");
            return showIdStream
                    .filter(dto -> {
                        if (StringUtil.isEnglishOnly(dto.getName())) {
                            return true;
                        } else {
                            logger.debug("Skipping show ID {} with non-English name: {}", dto.getId(), dto.getName());
                            return false;
                        }
                    })
                    .sorted(Comparator
                            .comparing(TMDBShowIdExportDTO::getPopularity, Comparator.nullsLast(Comparator.reverseOrder()))
                            .thenComparing(TMDBShowIdExportDTO::getName, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)));
        } catch (Exception e) {
            logger.error("Failed to retrieve show IDs for date {}: {}", date, e.getMessage(), e);
            return Stream.empty();
        }
    }

    @Transactional
    public void processShow(Long showId) {
        // TODO: Method too long

        logger.info("Initiating processing for show with TMDB ID: {}", showId);
        Optional<Show> existingShowOptional = showRepository.findById(showId);

        if (existingShowOptional.isPresent()) {
            Show existingShow = existingShowOptional.get();
            if (existingShow.getStatus().equals("Ended")) {
                logger.info("Skipping processing for show ID {} as it has ended", showId);
                return;
            }

            Optional<TMDBShowDTO> showDTOOptionalForCheck = tmdbApiClient.getTMDBShowDetails(showId);
            if (showDTOOptionalForCheck.isPresent()) {
                TMDBShowDTO latestShowDTO = showDTOOptionalForCheck.get();
                if (existingShow.getNumberOfEpisodes() != null &&
                    existingShow.getNumberOfEpisodes().equals(latestShowDTO.getNumberOfEpisodes())) {
                    logger.info("Skipping processing for show ID {} as the episode count has not changed", showId);
                    return;
                }
            } else {
                logger.warn("Could not retrieve show details for ID {} to check episode count. " +
                        "Proceeding with full ingestion", showId);
            }
        }

        Optional<TMDBShowDTO> showDTOOptional = tmdbApiClient.getTMDBShowDetails(showId);

        if (showDTOOptional.isEmpty()) {
            logger.warn("Failed to retrieve details for show ID: {}", showId);
            return;
        }

        if (showDTOOptional.get().getType().equals("Talk Show")) {
            logger.info("Skipping processing for show ID {} as it is a Talk Show", showId);
            return;
        }

        TMDBShowDTO showDTO = showDTOOptional.get();

        // TODO: Implement business rule to skip talk shows, news, etc

        // If the show is already in the database, update; otherwise create a new Show entity
        Show show = existingShowOptional.orElseGet(Show::new);
        showMapper.updateShowFromDTO(showDTO, show);
        show.setId(showDTO.getId());
        show.setLastApiFetchTimestamp(LocalDateTime.now(ZoneId.of("America/New_York")));

        try {
            processGenres(showDTO.getGenres(), show);
            processCreators(showDTO.getCreatedBy(), show);
            processNetworks(showDTO.getNetworks(), show);
            processSeasons(showDTO.getSeasons(), show);

            for (Season season : show.getSeasons()) {
                ingestEpisodesBySeason(season, show);
            }

            showRepository.save(show);
            logger.info("Successfully processed and saved show {} with ID: {}", show.getName(), show.getId());
        } catch (Exception e) {
            logger.error("Error processing show ID {}: {}", showId, e.getMessage(), e);
        }
    }


    private void ingestEpisodesBySeason(Season season, Show show) {
        if (season.getEpisodeCount() == null || season.getEpisodeCount() <= 0) {
            logger.warn("Season {} for show ID {} has no episodes to process", season.getSeasonNumber(), show.getId());
            return;
        }

        logger.info("Processing episodes for show ID {} ({}) in season {}", show.getId(), show.getName(), season.getSeasonNumber());

        Set<Integer> existingEpisodeNumbers = season.getEpisodes().stream()
                .map(Episode::getEpisodeNumber)
                .collect(Collectors.toSet());

        for (int episodeNumber = 1; episodeNumber <= season.getEpisodeCount(); episodeNumber++) {
            if (existingEpisodeNumbers.contains(episodeNumber)) {
                logger.debug("S{}E{} for show ID {} already exists. Skipping.",
                        season.getSeasonNumber(), episodeNumber, show.getId());
                continue;
            }
            processEpisode(show.getId(), season, episodeNumber);
        }
    }

    private void processEpisode(Long showId, Season season, Integer episodeNumber) {
        logger.info("Attempting to retrieve S{}E{} details for show ID {}",
                season.getSeasonNumber(), episodeNumber, showId);

        Optional<TMDBEpisodeDTO> episodeDTOOptional;

        try {
            episodeDTOOptional = tmdbApiClient.getTMDBEpisodeDetails(showId, season.getSeasonNumber(), episodeNumber);
        } catch (Exception e) {
            logger.error("Error retrieving S{}E{} details for show ID {}: {}",
                    season.getSeasonNumber(), episodeNumber, showId, e.getMessage());
            return;
        }

        if (episodeDTOOptional.isEmpty()) {
            logger.warn("Failed to retrieve details for S{}E{} of show ID {}",
                    season.getSeasonNumber(), episodeNumber, showId);
            return;
        }

        TMDBEpisodeDTO episodeDTO = episodeDTOOptional.get();

        Optional<Episode> existingEpisodeOptional = episodeRepository.findBySeasonAndEpisodeNumber(season, episodeNumber);
        Episode episode = existingEpisodeOptional.orElseGet(Episode::new);


        episodeMapper.updateEpisodeFromDTO(episodeDTO, episode);
        episode.setSeason(season);

        if (existingEpisodeOptional.isEmpty()) {
            season.getEpisodes().add(episode);
            logger.info("Added new episode S{}E{} to season {} of show ID {}",
                    episode.getSeason().getSeasonNumber(), episode.getEpisodeNumber(), season.getId(), showId);
        } else {
            logger.info("Updated existing episode S{}E{} for season {} of show ID {}",
                    episode.getSeason().getSeasonNumber(), episode.getEpisodeNumber(), season.getId(), showId);
        }

        try {
            episodeRepository.save(episode);
            logger.debug("Successfully saved episode S{}E{} for show ID {}",
                    episode.getSeason().getSeasonNumber(), episode.getEpisodeNumber(), showId);
        } catch (Exception e) {
            logger.error("Error saving episode S{}E{} for show ID {}: {}",
                    episode.getSeason().getSeasonNumber(), episode.getEpisodeNumber(), showId, e.getMessage());
        }
    }

    private void processGenres(List<TMDBShowDTO.TMDBGenreDTO> genreDTOs, Show show) {
        if (genreDTOs == null || genreDTOs.isEmpty()) {
            show.setGenres(List.of());
            return;
        }

        List<Genre> currentGenres = new ArrayList<>();

        for (TMDBShowDTO.TMDBGenreDTO genreDTO : genreDTOs) {
            Genre genre = genreRepository.findById(genreDTO.getId())
                    .orElseGet(() -> genreMapper.toGenre(genreDTO));
            currentGenres.add(genre);
        }

        show.setGenres(currentGenres);
    }

    private void processCreators(List<TMDBShowDTO.TMDBCreatorDTO> creatorDTOs, Show show) {
        if (creatorDTOs == null || creatorDTOs.isEmpty()) {
            show.setCreators(List.of());
            return;
        }

        List<Creator> currentCreators = new ArrayList<>();

        for (TMDBShowDTO.TMDBCreatorDTO creatorDTO : creatorDTOs) {
            Creator creator = creatorRepository.findById(creatorDTO.getId())
                    .orElseGet(() -> creatorMapper.toCreator(creatorDTO));
            currentCreators.add(creator);
        }

        show.setCreators(currentCreators);
    }

    private void processNetworks(List<TMDBShowDTO.TMDBNetworkDTO> networkDTOs, Show show) {
        if (networkDTOs == null || networkDTOs.isEmpty()) {
            show.setNetworks(List.of());
            return;
        }

        List<Network> currentNetworks = new ArrayList<>();

        for (TMDBShowDTO.TMDBNetworkDTO networkDTO : networkDTOs) {
            Network network = networkRepository.findById(networkDTO.getId())
                    .orElseGet(() -> networkMapper.toNetwork(networkDTO));
            currentNetworks.add(network);
        }

        show.setNetworks(currentNetworks);
    }

    private void processSeasons(List<TMDBShowDTO.TMDBSeasonSummaryDTO> seasonDTOs, Show show) {
        if (seasonDTOs == null || seasonDTOs.isEmpty()) {
            show.setSeasons(List.of());
            return;
        }
        List<Season> currentSeasons = new ArrayList<>();

        for (TMDBShowDTO.TMDBSeasonSummaryDTO seasonDTO : seasonDTOs) {
            Season season = seasonRepository.findById(seasonDTO.getId())
                    .orElseGet(() -> seasonMapper.toSeason(seasonDTO));
            currentSeasons.add(season);
        }

        show.setSeasons(currentSeasons);
        seasonRepository.saveAll(currentSeasons);

    }
}

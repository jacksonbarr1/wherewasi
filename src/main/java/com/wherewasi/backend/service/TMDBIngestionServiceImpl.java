package com.wherewasi.backend.service;

import com.wherewasi.backend.client.TMDBApiClient;
import com.wherewasi.backend.dto.tmdb.TMDBShowDTO;
import com.wherewasi.backend.dto.tmdb.TMDBShowIdExportDTO;
import com.wherewasi.backend.entity.*;
import com.wherewasi.backend.mapper.*;
import com.wherewasi.backend.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class TMDBIngestionServiceImpl implements TMDBIngestionService {

    // TODO: Use generics to decouple processing logic from specific nested DTO types

    private static final Logger logger = LoggerFactory.getLogger(TMDBIngestionServiceImpl.class);

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private CreatorRepository creatorRepository;

    @Autowired
    private NetworkRepository networkRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private TMDBApiClient tmdbApiClient;

    @Autowired
    private ShowMapper showMapper;

    @Autowired
    private GenreMapper genreMapper;

    @Autowired
    private CreatorMapper creatorMapper;

    @Autowired
    private NetworkMapper networkMapper;

    @Autowired
    private SeasonMapper seasonMapper;

    @Override
    @Scheduled
    public void dailyIngestionJob() {
        // Get exported id file, filter, sort
        Stream<TMDBShowIdExportDTO> processedStream = processDatedIdFile(LocalDate.now());

        // Retrieve and persist details for each show in the stream
        processedStream.forEach(tmdbShowIdExportDTO -> {
            processShow(tmdbShowIdExportDTO.getId());
        });
    }

    private Stream<TMDBShowIdExportDTO> processDatedIdFile(LocalDate date) {
        // Returns an unfiltered, unsorted stream of IDs for every show on TMDB
        Stream<TMDBShowIdExportDTO> showIdStream = tmdbApiClient.downloadAndStreamShowIds(date);
        logger.info("Successfully retrieved show IDs for date: {}", date);

        logger.info("Filtering and sorting retrieved show IDs");
        return showIdStream
                .filter(this::isEnglishName)
                .sorted(Comparator
                        .comparing(TMDBShowIdExportDTO::getPopularity, Comparator.nullsLast(Comparator.reverseOrder()))
                        .thenComparing(TMDBShowIdExportDTO::getName, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)));
    }

    void processShow(Long showId) {
        logger.info("Processing show with ID: {}", showId);
        Optional<TMDBShowDTO> showDTOOptional = tmdbApiClient.getTMDBShowDetails(showId);

        if (showDTOOptional.isEmpty()) {
            logger.warn("Failed to retrieve details for show ID: {}", showId);
            return;
        }


        TMDBShowDTO showDTO = showDTOOptional.get();
        logger.info("Successfully retrieved details for show ID: {} ({})", showDTO.getId(), showDTO.getName());

        Show show = showRepository.findById(showId).orElseGet(Show::new);

        showMapper.updateShowFromDTO(showDTO, show);
        show.setId(showDTO.getId());
        show.setLastApiFetchTimestamp(LocalDateTime.now());

        // Map nested DTOs to entities, persist and set them in the show entity
        processGenres(showDTO.getGenres(), show);
        processCreators(showDTO.getCreatedBy(), show);
        processNetworks(showDTO.getNetworks(), show);
        processSeasons(showDTO.getSeasons(), show);

        showRepository.save(show);
    }

    private void processEpisode(Long showId, Integer seasonNumber, Integer episodeNumber) {
        // Implementation for processing an episode by show ID, season number, and episode number
    }

    private void processGenres(List<TMDBShowDTO.TMDBGenreDTO> genreDTOs, Show show) {
        if (genreDTOs == null || genreDTOs.isEmpty()) {
            show.setGenres(List.of());
            return;
        }

        List<Genre> currentGenres = new ArrayList<>();

        for (TMDBShowDTO.TMDBGenreDTO genreDTO : genreDTOs) {
            Genre genre = genreRepository.findById(genreDTO.getId())
                    .orElseGet(() -> {
                        Genre newGenre = genreMapper.toGenre(genreDTO);
                        return genreRepository.save(newGenre);
                    });
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
                    .orElseGet(() -> {
                        Creator newCreator = creatorMapper.toCreator(creatorDTO);
                        return creatorRepository.save(newCreator);
                    });
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
                    .orElseGet(() -> {
                        Network newNetwork = networkMapper.toNetwork(networkDTO);
                        return networkRepository.save(newNetwork);
                    });
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
                    .orElseGet(() -> {
                        Season newSeason = seasonMapper.toSeason(seasonDTO);
                        return seasonRepository.save(newSeason);
                    });
            currentSeasons.add(season);
        }

        show.setSeasons(currentSeasons);
    }

    boolean isEnglishName(TMDBShowIdExportDTO dto) {
        if (dto == null || dto.getName() == null) {
            return false;
        }

        return dto.getName().chars().allMatch(c -> c >= 32 && c <= 126);
    }
}

package com.wherewasi.backend.dto.tmdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wherewasi.backend.AbstractTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class TMDBShowDTOTest extends AbstractTest {

    private ObjectMapper mapper;
    private String exampleJson;

    @BeforeEach
    void setUp() throws IOException {
        mapper = new ObjectMapper();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("example_tmdb_show_response.json")) {
            assertNotNull(input, "Example JSON file not found");
            exampleJson = new String(input.readAllBytes());
        }
    }

    @Test
    @DisplayName("Should correctly deserialize TMDBShowDTO from JSON")
    void shouldDeserializeTMDBShowDTO() throws IOException {
        TMDBShowDTO showDTO = mapper.readValue(exampleJson, TMDBShowDTO.class);

        assertNotNull(showDTO, "Deserialized TMDBShowDTO should not be null");

        // Top level fields only
        assertFalse(showDTO.getAdult());
        assertEquals("/bX6Sypdpk0r8YFdVPoc3yeyvSmm.jpg", showDTO.getBackdropPath());
        assertEquals("2005-03-24", showDTO.getFirstAirDate());
        assertEquals("https://www.nbc.com/the-office", showDTO.getHomepage());
        assertFalse(showDTO.getIsInProduction());
        assertEquals(List.of("en"), showDTO.getLanguages());
        assertEquals("2013-05-16", showDTO.getLastAirDate());
        assertEquals("The Office", showDTO.getName());
        assertEquals(186, showDTO.getNumberOfEpisodes());
        assertEquals(9, showDTO.getNumberOfSeasons());
        assertEquals(List.of("US"), showDTO.getOriginCountry());
        assertEquals("en", showDTO.getOriginalLanguage());
        assertEquals("The Office", showDTO.getOriginalName());
        assertEquals("The everyday lives of office employees in the " +
                "Scranton, Pennsylvania branch of the fictional Dunder Mifflin Paper Company.", showDTO.getOverview());
        // Skipping varying popularity and vote fields
        assertEquals("/7DJKHzAi83BmQrWLrYYOqcoKfhR.jpg", showDTO.getPosterPath());
        assertEquals("Ended", showDTO.getStatus());
        assertEquals("A comedy for anyone whose boss is an idiot.", showDTO.getTagline());
        assertEquals("Scripted", showDTO.getType());

        // Test nested DTOs
        testCreators(showDTO.getCreatedBy());
        testNetworks(showDTO.getNetworks());
        testGenres(showDTO.getGenres());
        testEpisodeSummaryDTO(showDTO.getLastEpisodeToAir());
        testSeasonSummaryDTO(showDTO.getSeasons().getLast());

    }

    private void testCreators(List<TMDBShowDTO.TMDBCreatorDTO> createdBy) {
        assertNotNull(createdBy, "Creators list should not be null");

        assertEquals(1, createdBy.size());
        TMDBShowDTO.TMDBCreatorDTO creator = createdBy.getFirst();

        assertEquals(1216630L, creator.getId());
        assertEquals("525730af760ee3776a344e95", creator.getCreditId());
        assertEquals("Greg Daniels", creator.getName());
        assertEquals("Greg Daniels", creator.getOriginalName());
        assertEquals(2, creator.getGender());
        assertEquals("/2Hi7Tw0fyYFOZex8BuGsHS8Q4KD.jpg", creator.getProfilePath());
    }

    private void testNetworks(List<TMDBShowDTO.TMDBNetworkDTO> networks) {
        assertNotNull(networks, "Networks list should not be null");

        assertEquals(1, networks.size());
        TMDBShowDTO.TMDBNetworkDTO network = networks.getFirst();

        assertEquals(6, network.getId());
        assertEquals("NBC", network.getName());
        assertEquals("/cm111bsDVlYaC1foL0itvEI4yLG.png", network.getLogoPath());
        assertEquals("US", network.getOriginCountry());
    }

    private void testGenres(List<TMDBShowDTO.TMDBGenreDTO> genres) {
        assertNotNull(genres, "Genres list should not be null");

        assertEquals(1, genres.size());
        TMDBShowDTO.TMDBGenreDTO genre = genres.getFirst();

        assertEquals(35, genre.getId());
        assertEquals("Comedy", genre.getName());
    }

    private void testEpisodeSummaryDTO(TMDBShowDTO.TMDBEpisodeSummaryDTO lastEpisodeToAir) {
        assertNotNull(lastEpisodeToAir, "Last episode to air should not be null");

        assertEquals(170335, lastEpisodeToAir.getId());
        assertEquals("Finale", lastEpisodeToAir.getName());
        assertEquals("One year later, Dunder Mifflin employees past and present reunite for a panel discussion " +
                "about the documentary and to attend Dwight and Angela's wedding.", lastEpisodeToAir.getOverview());
        assertEquals("2013-05-16", lastEpisodeToAir.getAirDate());
        assertEquals(23, lastEpisodeToAir.getEpisodeNumber());
        assertEquals("finale", lastEpisodeToAir.getEpisodeType());
        assertEquals(44, lastEpisodeToAir.getRuntime());
        assertEquals(9, lastEpisodeToAir.getSeasonNumber());
        assertEquals(2316, lastEpisodeToAir.getShowId());
        assertEquals("/1pJtFmAS3TJuWoCaUrY904pqNFE.jpg", lastEpisodeToAir.getStillPath());
    }

    private void testSeasonSummaryDTO(TMDBShowDTO.TMDBSeasonSummaryDTO lastSeason) {
        assertNotNull(lastSeason, "Last season should not be null");

        assertEquals("2012-09-20", lastSeason.getAirDate());
        assertEquals(23, lastSeason.getEpisodeCount());
        assertEquals(7249, lastSeason.getId());
        assertEquals("Season 9", lastSeason.getName());
        assertEquals("In Season 9, Andy finds his true calling in showbiz, Jim lands the job of his dreams, " +
                "Erin struggles with love, and Angela's marriage isn’t what it seems.", lastSeason.getOverview());
        assertEquals("/ncR9y2RApOTSjfggJHjvoIlAqDG.jpg", lastSeason.getPosterPath());
        assertEquals(9, lastSeason.getSeasonNumber());
    }
}
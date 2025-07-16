package com.wherewasi.backend.dto.tmdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TMDBShowDTOTest {

    @Test
    void testJsonMappingToDTO() throws IOException {
        String json = "{ \"id\": 2316, \"name\": \"The Office\", \"seasons\": [{ \"id\": 7240, \"name\": \"Season 1\", \"season_number\": 1, \"episode_count\": 6 }], \"genres\": [{ \"id\": 35, \"name\": \"Comedy\" }], \"created_by\": [{ \"id\": 1216630, \"name\": \"Greg Daniels\" }] }";

        ObjectMapper mapper = new ObjectMapper();
        TMDBShowDTO dto = mapper.readValue(json, TMDBShowDTO.class);

        assertEquals(2316L, dto.getId());
        assertEquals("The Office", dto.getName());
        assertNotNull(dto.getSeasons());
        assertEquals(1, dto.getSeasons().size());
        assertEquals("Season 1", dto.getSeasons().get(0).getName());
        assertNotNull(dto.getGenres());
        assertEquals("Comedy", dto.getGenres().get(0).name());
        assertNotNull(dto.getCreators());
        assertEquals("Greg Daniels", dto.getCreators().get(0).name());
    }
}
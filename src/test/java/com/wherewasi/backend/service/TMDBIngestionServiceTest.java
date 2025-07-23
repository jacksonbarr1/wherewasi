package com.wherewasi.backend.service;

import com.wherewasi.backend.AbstractTest;
import com.wherewasi.backend.dto.tmdb.TMDBShowIdExportDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TMDBIngestionServiceTest extends AbstractTest {

    private final TMDBIngestionServiceImpl tmdbIngestionService = new TMDBIngestionServiceImpl();

    @Test
    void whenGivenEnglishOnlyString_thenReturnsTrue() {
        TMDBShowIdExportDTO example = TMDBShowIdExportDTO.builder()
                .name("English: 101")
                .build();

        boolean result = tmdbIngestionService.isEnglishName(example);

        assertTrue(result);
    }

    @Test
    void whenGivenNonEnglishOnlyString_thenReturnsTrue() {
        TMDBShowIdExportDTO example = TMDBShowIdExportDTO.builder()
                .name("プライド")
                .build();

        boolean result = tmdbIngestionService.isEnglishName(example);

        assertFalse(result);
    }
}

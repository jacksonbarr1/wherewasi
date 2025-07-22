package com.wherewasi.backend.service;

import com.wherewasi.backend.AbstractTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TMDBIngestionServiceTest extends AbstractTest {

    private final TMDBIngestionServiceImpl tmdbIngestionService = new TMDBIngestionServiceImpl();

    @Test
    void whenGivenEnglishOnlyString_thenReturnsTrue() {
        String example = "English: 101";

        boolean result = tmdbIngestionService.isEnglishCharactersOnly(example);

        assertTrue(result);
    }

    @Test
    void whenGivenNonEnglishOnlyString_thenReturnsTrue() {
        String example = "プライド";

        boolean result = tmdbIngestionService.isEnglishCharactersOnly(example);

        assertFalse(result);
    }
}

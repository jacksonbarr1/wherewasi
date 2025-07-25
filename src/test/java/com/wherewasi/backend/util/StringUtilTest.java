package com.wherewasi.backend.util;

import com.wherewasi.backend.AbstractTest;
import com.wherewasi.backend.dto.tmdb.TMDBShowIdExportDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringUtilTest extends AbstractTest {

    @Test
    void whenGivenEnglishOnlyString_thenReturnsTrue() {
        assertTrue(StringUtil.isEnglishOnly("English: 101!"));
    }

    @Test
    void whenGivenNonEnglishOnlyString_thenReturnsTrue() {
        assertFalse(StringUtil.isEnglishOnly("プライド"));
    }
}

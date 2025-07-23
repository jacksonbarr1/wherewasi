package com.wherewasi.backend.service;

import com.wherewasi.backend.AbstractIT;
import com.wherewasi.backend.repository.ShowRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TMDBIngestionServiceIT extends AbstractIT {

    @Autowired
    private TMDBIngestionServiceImpl tmdbIngestionService;

    @Autowired
    private ShowRepository showRepository;

    @Test
    void testIngestShow() {
        tmdbIngestionService.processShow(2316L);

        assertTrue(showRepository.existsById(2316L), "Show with ID 2316 should exist after ingestion");
    }
}

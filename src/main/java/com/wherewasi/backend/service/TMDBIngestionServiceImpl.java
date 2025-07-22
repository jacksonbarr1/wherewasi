package com.wherewasi.backend.service;

import com.wherewasi.backend.client.TMDBApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;

public class TMDBIngestionServiceImpl implements TMDBIngestionService {

    @Autowired
    private TMDBApiClient tmdbApiClient;

    @Override
    @Scheduled
    public void dailyIngestionJob() {
        // Implementation for daily ingestion job
    }

    @Override
    public void processDatedIdFile(LocalDate date) {
    }

    @Override
    public void processShow(Long showId) {
        // Implementation for processing a show by its ID
    }

    @Override
    public void processEpisode(Long showId, Integer seasonNumber, Integer episodeNumber) {
        // Implementation for processing an episode by show ID, season number, and episode number
    }
}

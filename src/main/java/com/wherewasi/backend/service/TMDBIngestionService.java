package com.wherewasi.backend.service;

import java.time.LocalDate;

public interface TMDBIngestionService {
    void dailyIngestionJob();

    void processDatedIdFile(LocalDate date);

    void processShow(Long showId);

    void processEpisode(Long showId, Integer seasonNumber, Integer episodeNumber);
}

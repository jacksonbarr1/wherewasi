package com.wherewasi.backend.service;

import com.wherewasi.backend.client.TMDBApiClient;
import com.wherewasi.backend.dto.tmdb.TMDBShowIdExportDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.stream.Stream;

public class TMDBIngestionServiceImpl implements TMDBIngestionService {

    Logger logger = LoggerFactory.getLogger(TMDBIngestionServiceImpl.class);

    @Autowired
    private TMDBApiClient tmdbApiClient;

    @Override
    @Scheduled
    public void dailyIngestionJob() {
        // Get exported id file
        processDatedIdFile(LocalDate.now());
    }

    private void processDatedIdFile(LocalDate date) {
        // Returns an unfiltered, unsorted stream of IDs for every show on TMDB
        Stream<TMDBShowIdExportDTO> showIdStream = tmdbApiClient.downloadAndStreamShowIds(date);
    }

    private void processShow(Long showId) {
        // Implementation for processing a show by its ID
    }

    private void processEpisode(Long showId, Integer seasonNumber, Integer episodeNumber) {
        // Implementation for processing an episode by show ID, season number, and episode number
    }

    boolean isEnglishCharactersOnly(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        return str.chars().allMatch(c -> c >= 32 && c <= 126);
    }
}

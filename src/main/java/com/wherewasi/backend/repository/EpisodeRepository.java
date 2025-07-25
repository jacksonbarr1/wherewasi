package com.wherewasi.backend.repository;

import com.wherewasi.backend.entity.Episode;
import com.wherewasi.backend.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    Optional<Episode> findBySeasonAndEpisodeNumber(Season season, Integer episodeNumber);
}

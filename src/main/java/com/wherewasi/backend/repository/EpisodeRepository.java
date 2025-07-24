package com.wherewasi.backend.repository;

import com.wherewasi.backend.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
}

package com.wherewasi.backend.repository;

import com.wherewasi.backend.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonRepository extends JpaRepository<Season, Long> {
}

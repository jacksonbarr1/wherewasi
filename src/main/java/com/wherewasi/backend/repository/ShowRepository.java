package com.wherewasi.backend.repository;

import com.wherewasi.backend.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show, Long> {
}
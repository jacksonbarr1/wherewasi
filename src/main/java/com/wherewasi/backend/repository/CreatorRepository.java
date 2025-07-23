package com.wherewasi.backend.repository;


import com.wherewasi.backend.entity.Creator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatorRepository extends JpaRepository<Creator, Long> {
}

package com.wherewasi.backend.controller;

import com.wherewasi.backend.service.TMDBIngestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tmdb")
public class TMDBIngestionController {

    @Autowired
    private TMDBIngestionServiceImpl tmdbIngestionService;

    @PostMapping("/show/{showId}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<String> ingestShow(@PathVariable Long showId) {
        tmdbIngestionService.processShow(showId);

        return ResponseEntity.ok("Show with ID " + showId + " has been successfully ingested.");
    }
}

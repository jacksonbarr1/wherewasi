package com.wherewasi.backend.dto.tmdb;

import lombok.Builder;

@Builder
public record TMDBGenreDTO(Long id, String name) {
}
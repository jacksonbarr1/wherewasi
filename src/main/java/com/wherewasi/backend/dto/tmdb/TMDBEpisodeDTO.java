package com.wherewasi.backend.dto.tmdb;

import lombok.Builder;

import java.util.Date;

@Builder
public record TMDBEpisodeDTO(Long id, Integer episodeNumber, Date airDate, String name, String overview,
                             Float voteAverage, Integer voteCount) {
}
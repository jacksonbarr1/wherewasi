package com.wherewasi.backend.dto.tmdb;

import java.util.Date;

public record TMDBEpisodeDTO(Long id, Integer episodeNumber, Date airDate, String name, String overview,
                             Float voteAverage, Integer voteCount) {
}
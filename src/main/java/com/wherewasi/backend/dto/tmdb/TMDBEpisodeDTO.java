package com.wherewasi.backend.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TMDBEpisodeDTO {
    @JsonProperty("air_date")
    private String airDate;
    @JsonProperty("episode_number")
    private Integer episodeNumber;
    @JsonProperty("episode_type")
    private String episodeType;
    private String name;
    private String overview;
    private Long id;
    private Integer runtime;
    @JsonProperty("season_number")
    private Integer seasonNumber;
    @JsonProperty("still_path")
    private String stillPath;
    @JsonProperty("vote_average")
    private Float voteAverage;
    @JsonProperty("vote_count")
    private Integer voteCount;

}

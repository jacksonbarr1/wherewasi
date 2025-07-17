package com.wherewasi.backend.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TMDBSeasonDTO {
    private Long id;
    private String name;
    private List<TMDBEpisodeDTO> episodes;

    @JsonProperty("air_date")
    private String airDate;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("season_number")
    private Integer seasonNumber;

    @JsonProperty("episode_count")
    private Integer episodeCount;
}

package com.wherewasi.backend.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TMDBShowDTO {
    private Long id;
    private Boolean adult;
    @JsonProperty("backdrop_path")
    private String backdropPath;
    @JsonProperty("created_by")
    private List<TMDBCreatorDTO> createdBy;
    @JsonProperty("first_air_date")
    private String firstAirDate;
    private List<TMDBGenreDTO> genres;
    private String homepage;
    @JsonProperty("in_production")
    private Boolean isInProduction;
    private List<String> languages;
    @JsonProperty("last_air_date")
    private String lastAirDate;
    @JsonProperty("last_episode_to_air")
    private TMDBEpisodeSummaryDTO lastEpisodeToAir;
    private String name;
    @JsonProperty("next_episode_to_air")
    private TMDBEpisodeSummaryDTO nextEpisodeToAir;
    private List<TMDBNetworkDTO> networks;
    @JsonProperty("number_of_episodes")
    private Integer numberOfEpisodes;
    @JsonProperty("number_of_seasons")
    private Integer numberOfSeasons;
    @JsonProperty("origin_country")
    private List<String> originCountry;
    @JsonProperty("original_language")
    private String originalLanguage;
    @JsonProperty("original_name")
    private String originalName;
    private String overview;
    private Float popularity;
    @JsonProperty("poster_path")
    private String posterPath;
    private List<TMDBSeasonSummaryDTO> seasons;
    private String status;
    private String tagline;
    private String type;
    @JsonProperty("vote_average")
    private Float voteAverage;
    @JsonProperty("vote_count")
    private Integer voteCount;

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TMDBCreatorDTO {
        private Long id;
        @JsonProperty("credit_id")
        private String creditId;
        private String name;
        private Integer gender;
        @JsonProperty("original_name")
        private String originalName;
        @JsonProperty("profile_path")
        private String profilePath;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TMDBGenreDTO {
        private Long id;
        private String name;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TMDBEpisodeSummaryDTO {
        private Long id;
        private String name;
        private String overview;
        @JsonProperty("vote_average")
        private Float voteAverage;
        @JsonProperty("vote_count")
        private Integer voteCount;
        @JsonProperty("air_date")
        private String airDate;
        @JsonProperty("episode_number")
        private Integer episodeNumber;
        @JsonProperty("episode_type")
        private String episodeType;
        private Integer runtime;
        @JsonProperty("season_number")
        private Integer seasonNumber;
        @JsonProperty("show_id")
        private Long showId;
        @JsonProperty("still_path")
        private String stillPath;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TMDBSeasonSummaryDTO {
        @JsonProperty("air_date")
        private String airDate;
        @JsonProperty("episode_count")
        private Integer episodeCount;
        private Long id;
        private String name;
        private String overview;
        @JsonProperty("poster_path")
        private String posterPath;
        @JsonProperty("season_number")
        private Integer seasonNumber;
        @JsonProperty("vote_average")
        private Float voteAverage;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TMDBNetworkDTO {
        private Long id;
        @JsonProperty("logo_path")
        private String logoPath;
        private String name;
        @JsonProperty("origin_country")
        private String originCountry;
    }

}

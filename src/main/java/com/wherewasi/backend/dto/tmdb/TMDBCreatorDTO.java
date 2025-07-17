package com.wherewasi.backend.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TMDBCreatorDTO(Long id, String name, @JsonProperty("image_path") String imagePath) {
}
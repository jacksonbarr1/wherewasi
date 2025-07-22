package com.wherewasi.backend.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TMDBShowIdExportDTO {
    private Long id;
    @JsonProperty("original_name")
    private String name;
    private Float popularity;
}

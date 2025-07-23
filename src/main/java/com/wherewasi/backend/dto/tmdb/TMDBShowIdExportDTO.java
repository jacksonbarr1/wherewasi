package com.wherewasi.backend.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TMDBShowIdExportDTO {
    private Long id;
    @JsonProperty("original_name")
    private String name;
    private Float popularity;
}

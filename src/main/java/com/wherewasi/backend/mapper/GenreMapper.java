package com.wherewasi.backend.mapper;

import com.wherewasi.backend.dto.tmdb.TMDBGenreDTO;
import com.wherewasi.backend.model.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    Genre toEntity(TMDBGenreDTO genreDTO);
}
package com.wherewasi.backend.mapper;

import com.wherewasi.backend.dto.tmdb.TMDBGenreDTO;
import com.wherewasi.backend.model.Genre;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface GenreMapper {
    Genre toEntity(TMDBGenreDTO genreDTO);
}
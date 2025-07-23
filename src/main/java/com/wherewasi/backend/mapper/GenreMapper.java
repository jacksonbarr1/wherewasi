package com.wherewasi.backend.mapper;

import com.wherewasi.backend.dto.tmdb.TMDBShowDTO;
import com.wherewasi.backend.entity.Genre;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    Genre toGenre(TMDBShowDTO.TMDBGenreDTO genreDTO);

    List<Genre> toGenreList(List<TMDBShowDTO.TMDBGenreDTO> tmdbGenreDTOlist);
}

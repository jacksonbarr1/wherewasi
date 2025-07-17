package com.wherewasi.backend.mapper;

import com.wherewasi.backend.dto.tmdb.TMDBShowDTO;
import com.wherewasi.backend.model.Show;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {SeasonMapper.class, GenreMapper.class, CreatorMapper.class})
public interface ShowMapper {

    Show toEntity(TMDBShowDTO showDTO);
}

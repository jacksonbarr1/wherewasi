package com.wherewasi.backend.mapper;

import com.wherewasi.backend.dto.tmdb.TMDBShowDTO;
import com.wherewasi.backend.entity.Show;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {SeasonMapper.class, GenreMapper.class, CreatorMapper.class})
@Component
public interface ShowMapper {

    Show toEntity(TMDBShowDTO showDTO);
}

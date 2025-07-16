package com.wherewasi.backend.mapper;

import com.wherewasi.backend.dto.tmdb.TMDBSeasonDTO;
import com.wherewasi.backend.model.Season;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {EpisodeMapper.class})
public interface SeasonMapper {

    Season toEntity(TMDBSeasonDTO seasonDTO);
}
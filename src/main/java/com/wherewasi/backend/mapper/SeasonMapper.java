package com.wherewasi.backend.mapper;

import com.wherewasi.backend.dto.tmdb.TMDBSeasonDTO;
import com.wherewasi.backend.entity.Season;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EpisodeMapper.class})
public interface SeasonMapper {

    @Mapping(target = "seasonName", source = "name")
    Season toEntity(TMDBSeasonDTO seasonDTO);
}
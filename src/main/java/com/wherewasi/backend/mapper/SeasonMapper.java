package com.wherewasi.backend.mapper;

import com.wherewasi.backend.dto.tmdb.TMDBSeasonDTO;
import com.wherewasi.backend.model.Season;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {EpisodeMapper.class})
@Component
public interface SeasonMapper {

    Season toEntity(TMDBSeasonDTO seasonDTO);
}
package com.wherewasi.backend.mapper;

import com.wherewasi.backend.dto.tmdb.TMDBShowDTO;
import com.wherewasi.backend.entity.Season;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        DateMapper.class
})
public interface SeasonMapper {
    @Mapping(target = "airDate", source = "airDate", qualifiedByName = "stringToLocalDate")
    @Mapping(target = "episodes", ignore = true)
    Season toSeason(TMDBShowDTO.TMDBSeasonSummaryDTO tmdbSeasonDTO);

    List<Season> toSeasonList(List<TMDBShowDTO.TMDBSeasonSummaryDTO> tmdbSeasonDTOList);
}

package com.wherewasi.backend.mapper;

import com.wherewasi.backend.dto.tmdb.TMDBShowDTO;
import com.wherewasi.backend.entity.Episode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EpisodeMapper {
    Episode summaryDTOToEpisode(TMDBShowDTO.TMDBEpisodeSummaryDTO episodeSummaryDTO);
}
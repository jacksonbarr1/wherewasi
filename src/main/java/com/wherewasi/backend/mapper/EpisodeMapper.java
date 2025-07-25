package com.wherewasi.backend.mapper;

import com.wherewasi.backend.dto.tmdb.TMDBEpisodeDTO;
import com.wherewasi.backend.dto.tmdb.TMDBShowDTO;
import com.wherewasi.backend.entity.Episode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {
        DateMapper.class
})
public interface EpisodeMapper {
    Episode summaryDTOToEpisode(TMDBShowDTO.TMDBEpisodeSummaryDTO episodeSummaryDTO);

    @Mapping(target = "airDate", source = "airDate", qualifiedByName = "stringToLocalDate")
    Episode dtoToEpisode(TMDBEpisodeDTO episodeDTO);

    @Mapping(target = "airDate", source = "airDate", qualifiedByName = "stringToLocalDate")
    void updateEpisodeFromDTO(TMDBEpisodeDTO episodeDTO, @MappingTarget Episode episode);
}
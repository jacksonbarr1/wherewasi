package com.wherewasi.backend.mapper;

import com.wherewasi.backend.dto.tmdb.TMDBEpisodeDTO;
import com.wherewasi.backend.model.Episode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EpisodeMapper {

    Episode toEntity(TMDBEpisodeDTO episodeDTO);
}
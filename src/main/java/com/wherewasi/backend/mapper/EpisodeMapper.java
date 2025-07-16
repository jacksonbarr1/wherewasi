package com.wherewasi.backend.mapper;

import com.wherewasi.backend.dto.tmdb.TMDBEpisodeDTO;
import com.wherewasi.backend.model.Episode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface EpisodeMapper {

    Episode toEntity(TMDBEpisodeDTO episodeDTO);
}
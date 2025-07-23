package com.wherewasi.backend.mapper;

import com.wherewasi.backend.dto.tmdb.TMDBShowDTO;
import com.wherewasi.backend.entity.Creator;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CreatorMapper {

    Creator toCreator(TMDBShowDTO.TMDBCreatorDTO creatorDTO);

    List<Creator> toCreatorList(List<TMDBShowDTO.TMDBCreatorDTO> tmdbCreatorDTOList);
}

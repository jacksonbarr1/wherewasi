package com.wherewasi.backend.mapper;

import com.wherewasi.backend.dto.tmdb.TMDBCreatorDTO;
import com.wherewasi.backend.model.Creator;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreatorMapper {
    Creator toEntity(TMDBCreatorDTO creatorDTO);
}
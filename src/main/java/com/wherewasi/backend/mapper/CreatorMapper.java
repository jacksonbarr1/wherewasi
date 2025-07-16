package com.wherewasi.backend.mapper;

import com.wherewasi.backend.dto.tmdb.TMDBCreatorDTO;
import com.wherewasi.backend.model.Creator;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CreatorMapper {
    Creator toEntity(TMDBCreatorDTO creatorDTO);
}
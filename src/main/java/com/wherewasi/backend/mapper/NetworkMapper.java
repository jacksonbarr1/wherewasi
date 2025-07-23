package com.wherewasi.backend.mapper;

import com.wherewasi.backend.dto.tmdb.TMDBShowDTO;
import com.wherewasi.backend.entity.Network;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NetworkMapper {

    Network toNetwork(TMDBShowDTO.TMDBNetworkDTO networkDTO);

    List<Network> toNetworkList(List<TMDBShowDTO.TMDBNetworkDTO> tmdbNetworkDTOList);
}

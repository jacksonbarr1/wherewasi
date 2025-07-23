package com.wherewasi.backend.mapper;

import com.wherewasi.backend.dto.tmdb.TMDBShowDTO;
import com.wherewasi.backend.entity.Show;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {
        GenreMapper.class,
        CreatorMapper.class,
        NetworkMapper.class,
        SeasonMapper.class,
        DateMapper.class
})
public interface ShowMapper {

    @Mapping(target = "firstAirDate", source = "firstAirDate", qualifiedByName = "stringToLocalDate")
    @Mapping(target = "lastAirDate", source = "lastAirDate", qualifiedByName = "stringToLocalDate")
    @Mapping(target = "seasons", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "creators", ignore = true)
    @Mapping(target = "networks", ignore = true)
    @Mapping(target = "lastApiFetchTimestamp", ignore = true)
    Show toShow(TMDBShowDTO tmdbShowDTO);

    @Mapping(target = "id", ignore = true) // ID should not be updated
    @Mapping(target = "firstAirDate", source = "firstAirDate", qualifiedByName = "stringToLocalDate")
    @Mapping(target = "lastAirDate", source = "lastAirDate", qualifiedByName = "stringToLocalDate")
    @Mapping(target = "isInProduction", source = "isInProduction")
    @Mapping(target = "seasons", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "creators", ignore = true)
    @Mapping(target = "networks", ignore = true)
    @Mapping(target = "lastApiFetchTimestamp", ignore = true)
    void updateShowFromDTO(TMDBShowDTO tmdbShowDTO, @MappingTarget Show show);
}

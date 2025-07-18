package com.wherewasi.backend.mapper;

import com.wherewasi.backend.AbstractTest;
import com.wherewasi.backend.dto.tmdb.TMDBCreatorDTO;
import com.wherewasi.backend.entity.Creator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@ExtendWith(MockitoExtension.class)
public class CreatorMapperTest extends AbstractTest {

    @InjectMocks
    private CreatorMapperImpl creatorMapper;

    @Test
    void whenGivenProperCreatorDTO_thenToEntityReturnsCorrectCreator() {
        TMDBCreatorDTO creatorDTO = TMDBCreatorDTO.builder()
                .id(1L)
                .name("Creator Name")
                .imagePath("/path/to/profile.jpg")
                .build();

        Creator expectedCreator = Creator.builder()
                .id(1L)
                .name("Creator Name")
                .imagePath("/path/to/profile.jpg")
                .build();

        Creator actualCreator = creatorMapper.toEntity(creatorDTO);

        assertThat(actualCreator)
                .usingRecursiveComparison()
                .isEqualTo(expectedCreator);
    }

    @Test
    void whenGivenCreatorEntity_thenToDtoReturnsCorrectCreatorDTO() {
        Creator creator = Creator.builder()
                .id(1L)
                .name("Creator Name")
                .imagePath("/path/to/profile.jpg")
                .build();

        TMDBCreatorDTO expectedCreatorDTO = TMDBCreatorDTO.builder()
                .id(1L)
                .name("Creator Name")
                .imagePath("/path/to/profile.jpg")
                .build();

        TMDBCreatorDTO actualCreatorDTO = creatorMapper.toDto(creator);

        assertThat(actualCreatorDTO)
                .usingRecursiveComparison()
                .isEqualTo(expectedCreatorDTO);
    }
}
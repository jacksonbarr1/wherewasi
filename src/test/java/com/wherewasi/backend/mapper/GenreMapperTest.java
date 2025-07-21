package com.wherewasi.backend.mapper;

import com.wherewasi.backend.AbstractTest;
import com.wherewasi.backend.dto.tmdb.TMDBGenreDTO;
import com.wherewasi.backend.entity.Genre;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class GenreMapperTest extends AbstractTest {

    @InjectMocks
    private GenreMapperImpl genreMapper;

    @Test
    void whenGivenProperGenreDTO_thenToEntityReturnsCorrectGenre() {
        TMDBGenreDTO genreDTO = TMDBGenreDTO.builder()
                .id(1L)
                .name("Drama")
                .build();

        Genre expectedGenre = Genre.builder()
                .id(1L)
                .name("Drama")
                .build();

        Genre actualGenre = genreMapper.toEntity(genreDTO);

        assertThat(actualGenre)
                .usingRecursiveComparison()
                .isEqualTo(expectedGenre);
    }

    @Test
    void whenGivenGenreEntity_thenToDtoReturnsCorrectGenreDTO() {
        Genre genre = Genre.builder()
                .id(1L)
                .name("Drama")
                .build();

        TMDBGenreDTO expectedGenreDTO = TMDBGenreDTO.builder()
                .id(1L)
                .name("Drama")
                .build();

        TMDBGenreDTO actualGenreDTO = genreMapper.toDto(genre);

        assertThat(actualGenreDTO)
                .usingRecursiveComparison()
                .isEqualTo(expectedGenreDTO);
    }
}
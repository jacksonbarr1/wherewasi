package com.wherewasi.backend.mapper;

import com.wherewasi.backend.AbstractTest;
import com.wherewasi.backend.dto.tmdb.TMDBEpisodeDTO;
import com.wherewasi.backend.entity.Episode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class EpisodeMapperTest extends AbstractTest {

    @InjectMocks
    private EpisodeMapperImpl episodeMapper;

    @Test
    void whenGivenProperEpisodeDTO_thenToEntityReturnsCorrectEpisode() {
        TMDBEpisodeDTO episodeDTO = TMDBEpisodeDTO.builder()
                .id(1L)
                .name("Episode 1")
                .episodeNumber(1)
                .airDate(new Date(2023, 1, 1))
                .overview("Overview of Episode 1")
                .voteAverage(8.5f)
                .build();

        Episode expectedEpisode = Episode.builder()
                .id(1L)
                .name("Episode 1")
                .episodeNumber(1)
                .airDate(new Date(2023, 1, 1))
                .overview("Overview of Episode 1")
                .voteAverage(8.5f)
                .build();

        Episode actualEpisode = episodeMapper.toEntity(episodeDTO);

        assertThat(actualEpisode)
                .usingRecursiveComparison()
                .isEqualTo(expectedEpisode);
    }

    @Test
    void whenGivenEpisodeEntity_thenToDtoReturnsCorrectEpisodeDTO() {
        Episode episode = Episode.builder()
                .id(1L)
                .name("Episode 1")
                .episodeNumber(1)
                .airDate(new Date(2023, 1, 1))
                .overview("Overview of Episode 1")
                .voteAverage(8.5f)
                .build();

        TMDBEpisodeDTO expectedEpisodeDTO = TMDBEpisodeDTO.builder()
                .id(1L)
                .name("Episode 1")
                .episodeNumber(1)
                .airDate(new Date(2023, 1, 1))
                .overview("Overview of Episode 1")
                .voteAverage(8.5f)
                .build();

        TMDBEpisodeDTO actualEpisodeDTO = episodeMapper.toDto(episode);

        assertThat(actualEpisodeDTO)
                .usingRecursiveComparison()
                .isEqualTo(expectedEpisodeDTO);
    }
}
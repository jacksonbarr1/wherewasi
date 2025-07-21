package com.wherewasi.backend.mapper;

import com.wherewasi.backend.AbstractTest;
import com.wherewasi.backend.dto.tmdb.TMDBEpisodeDTO;
import com.wherewasi.backend.dto.tmdb.TMDBSeasonDTO;
import com.wherewasi.backend.entity.Episode;
import com.wherewasi.backend.entity.Season;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SeasonMapperTest extends AbstractTest {
    @Mock
    private EpisodeMapper episodeMapper;

    @InjectMocks
    private SeasonMapperImpl seasonMapper;

    @Test
    void whenGivenProperSeasonDTO_thenToEntityReturnsCorrectSeason() {
        TMDBSeasonDTO seasonDTO = TMDBSeasonDTO.builder()
                .id(7240L)
                .name("Season 1")
                .seasonNumber(1)
                .episodeCount(2)
                .airDate(new Date(2023, 1, 1))
                .posterPath("/path/to/poster.jpg")
                .build();

        Episode expectedEpisodeOne = Episode.builder()
                .id(1L)
                .name("Episode 1")
                .episodeNumber(1)
                .airDate(new Date(2023, 1, 1))
                .overview("Overview of Episode 1")
                .build();

        Episode expectedEpisodeTwo = Episode.builder()
                .id(2L)
                .name("Episode 2")
                .episodeNumber(2)
                .airDate(new Date(2023, 1, 8))
                .overview("Overview of Episode 2")
                .build();

        seasonDTO.setEpisodes(List.of(
                TMDBEpisodeDTO.builder()
                        .id(1L)
                        .name("Episode 1")
                        .episodeNumber(1)
                        .airDate(new Date(2023, 1, 1))
                        .overview("Overview of Episode 1")
                        .build(),
                TMDBEpisodeDTO.builder()
                        .id(2L)
                        .name("Episode 2")
                        .episodeNumber(2)
                        .airDate(new Date(2023, 1, 8))
                        .overview("Overview of Episode 2")
                        .build()
        ));

        Season expectedSeason = Season.builder()
                .id(7240L)
                .seasonName("Season 1")
                .seasonNumber(1)
                .episodeCount(2)
                .episodes(List.of(expectedEpisodeOne, expectedEpisodeTwo))
                .build();

        when(episodeMapper.toEntity(seasonDTO.getEpisodes().get(0))).thenReturn(expectedEpisodeOne);
        when(episodeMapper.toEntity(seasonDTO.getEpisodes().get(1))).thenReturn(expectedEpisodeTwo);

        Season actualSeason = seasonMapper.toEntity(seasonDTO);

        assertThat(actualSeason)
                .usingRecursiveComparison()
                .isEqualTo(expectedSeason);
    }

    @Test
    void whenGivenSeasonEntity_thenToDtoReturnsCorrectSeasonDTO() {
        Season season = Season.builder()
                .id(7240L)
                .seasonName("Season 1")
                .seasonNumber(1)
                .episodeCount(2)
                .episodes(List.of(
                        Episode.builder().id(1L).name("Episode 1").episodeNumber(1).airDate(new Date(2023, 1, 1)).overview("Overview of Episode 1").build(),
                        Episode.builder().id(2L).name("Episode 2").episodeNumber(2).airDate(new Date(2023, 1, 8)).overview("Overview of Episode 2").build()
                ))
                .build();

        TMDBSeasonDTO expectedSeasonDTO = TMDBSeasonDTO.builder()
                .id(7240L)
                .name("Season 1")
                .seasonNumber(1)
                .episodeCount(2)
                .episodes(List.of(
                        TMDBEpisodeDTO.builder().id(1L).name("Episode 1").episodeNumber(1).airDate(new Date(2023, 1, 1)).overview("Overview of Episode 1").build(),
                        TMDBEpisodeDTO.builder().id(2L).name("Episode 2").episodeNumber(2).airDate(new Date(2023, 1, 8)).overview("Overview of Episode 2").build()
                ))
                .build();

        when(episodeMapper.toDto(season.getEpisodes().get(0))).thenReturn(expectedSeasonDTO.getEpisodes().get(0));
        when(episodeMapper.toDto(season.getEpisodes().get(1))).thenReturn(expectedSeasonDTO.getEpisodes().get(1));

        TMDBSeasonDTO actualSeasonDTO = seasonMapper.toDto(season);

        assertThat(actualSeasonDTO)
                .usingRecursiveComparison()
                .isEqualTo(expectedSeasonDTO);
    }
}

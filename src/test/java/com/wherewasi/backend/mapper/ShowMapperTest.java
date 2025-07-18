package com.wherewasi.backend.mapper;

import com.wherewasi.backend.AbstractTest;
import com.wherewasi.backend.dto.tmdb.*;
import com.wherewasi.backend.entity.Creator;
import com.wherewasi.backend.entity.Genre;
import com.wherewasi.backend.entity.Season;
import com.wherewasi.backend.entity.Show;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShowMapperTest extends AbstractTest {
    @Mock
    private SeasonMapper seasonMapper;

    @Mock
    private GenreMapper genreMapper;

    @Mock
    private CreatorMapper creatorMapper;

    @InjectMocks
    private ShowMapperImpl showMapper;

    @Test
    void whenGivenProperShowDTO_thenToEntityReturnsCorrectShow() {
        TMDBShowDTO showDTO = TMDBShowDTO.builder()
                .id(2316L)
                .name("name")
                .overview("overview")
                .popularity(86.143f)
                .firstAirDate(new Date(2023, 1, 1))
                .lastAirDate(new Date(2025, 1, 1))
                .posterPath("posterPath")
                .backdropPath("backdropPath")
                .voteAverage(8.58f)
                .voteCount(100)
                .seasons(List.of(TMDBSeasonDTO.builder().id(7240L).name("Season 1").seasonNumber(1).episodeCount(2).build()))
                .genres(List.of(TMDBGenreDTO.builder().id(1L).name("Drama").build()))
                .creators(List.of(TMDBCreatorDTO.builder().id(101L).name("Creator One").imagePath("/path/to/image1.jpg").build()))
                .build();

        Season expectedSeason = Season.builder().id(7240L).seasonName("Season 1").seasonNumber(1).episodeCount(2).build();
        Genre expectedGenre = Genre.builder().id(1L).name("Drama").build();
        Creator expectedCreator = Creator.builder().id(101L).name("Creator One").imagePath("/path/to/image1.jpg").build();

        Show expectedShow = Show.builder()
                .id(2316L)
                .name("name")
                .overview("overview")
                .popularity(86.143f)
                .firstAirDate(new Date(2023, 1, 1))
                .lastAirDate(new Date(2025, 1, 1))
                .posterPath("posterPath")
                .backdropPath("backdropPath")
                .voteAverage(8.58f)
                .voteCount(100)
                .seasons(List.of(expectedSeason))
                .genres(List.of(expectedGenre))
                .creators(List.of(expectedCreator))
                .build();

        when(seasonMapper.toEntity(showDTO.getSeasons().get(0))).thenReturn(expectedSeason);
        when(genreMapper.toEntity(showDTO.getGenres().get(0))).thenReturn(expectedGenre);
        when(creatorMapper.toEntity(showDTO.getCreators().get(0))).thenReturn(expectedCreator);

        Show actualShow = showMapper.toEntity(showDTO);

        assertAll("Ensure all Show fields are mapped correctly",
                () -> assertEquals(expectedShow.getId(), actualShow.getId()),
                () -> assertEquals(expectedShow.getName(), actualShow.getName()),
                () -> assertEquals(expectedShow.getOverview(), actualShow.getOverview()),
                () -> assertEquals(expectedShow.getPopularity(), actualShow.getPopularity()),
                () -> assertEquals(expectedShow.getFirstAirDate(), actualShow.getFirstAirDate()),
                () -> assertEquals(expectedShow.getLastAirDate(), actualShow.getLastAirDate()),
                () -> assertEquals(expectedShow.getPosterPath(), actualShow.getPosterPath()),
                () -> assertEquals(expectedShow.getBackdropPath(), actualShow.getBackdropPath()),
                () -> assertEquals(expectedShow.getVoteAverage(), actualShow.getVoteAverage()),
                () -> assertEquals(expectedShow.getVoteCount(), actualShow.getVoteCount()),
                () -> assertEquals(1, actualShow.getSeasons().size()),
                () -> assertEquals(expectedSeason, actualShow.getSeasons().get(0)),
                () -> assertEquals(1, actualShow.getGenres().size()),
                () -> assertEquals(expectedGenre, actualShow.getGenres().get(0)),
                () -> assertEquals(1, actualShow.getCreators().size()),
                () -> assertEquals(expectedCreator, actualShow.getCreators().get(0))
        );
    }
}

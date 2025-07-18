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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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

        assertThat(actualShow)
                .usingRecursiveComparison()
                .isEqualTo(expectedShow);
    }

    @Test
    void whenGivenNullShowDTO_thenToEntityReturnsNull() {
        TMDBShowDTO showDTO = null;

        Show actualShow = showMapper.toEntity(showDTO);

        assertEquals(null, actualShow);
    }

    @Test
    void whenGivenEmptyShowDTO_thenToEntityReturnsShowWithNullFields() {
        TMDBShowDTO showDTO = TMDBShowDTO.builder().build();

        Show actualShow = showMapper.toEntity(showDTO);

        assertAll("Ensure all Show fields are null",
                () -> assertNull(actualShow.getId()),
                () -> assertNull(actualShow.getName()),
                () -> assertNull(actualShow.getOverview()),
                () -> assertNull(actualShow.getPopularity()),
                () -> assertNull(actualShow.getFirstAirDate()),
                () -> assertNull(actualShow.getLastAirDate()),
                () -> assertNull(actualShow.getPosterPath()),
                () -> assertNull(actualShow.getBackdropPath()),
                () -> assertNull(actualShow.getVoteAverage()),
                () -> assertNull(actualShow.getVoteCount()),
                () -> assertNull(actualShow.getSeasons()),
                () -> assertNull(actualShow.getGenres()),
                () -> assertNull(actualShow.getCreators())
        );
    }

    @Test
    void whenGivenShowEntity_thenToDtoReturnsCorrectTMDBShowDTO() {
        Show show = Show.builder()
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
                .seasons(List.of(Season.builder().id(7240L).seasonName("Season 1").seasonNumber(1).episodeCount(2).build()))
                .genres(List.of(Genre.builder().id(1L).name("Drama").build()))
                .creators(List.of(Creator.builder().id(101L).name("Creator One").imagePath("/path/to/image1.jpg").build()))
                .build();

        TMDBShowDTO expectedShowDTO = TMDBShowDTO.builder()
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

        when(seasonMapper.toDto(show.getSeasons().get(0))).thenReturn(expectedShowDTO.getSeasons().get(0));
        when(genreMapper.toDto(show.getGenres().get(0))).thenReturn(expectedShowDTO.getGenres().get(0));
        when(creatorMapper.toDto(show.getCreators().get(0))).thenReturn(expectedShowDTO.getCreators().get(0));

        TMDBShowDTO actualShowDTO = showMapper.toDto(show);

        assertThat(actualShowDTO)
                .usingRecursiveComparison()
                .isEqualTo(expectedShowDTO);
        }
}

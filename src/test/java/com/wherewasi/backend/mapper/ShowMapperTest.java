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
                .build();

        TMDBSeasonDTO seasonDTO = TMDBSeasonDTO.builder()
                .id(7240L)
                .name("Season 1")
                .seasonNumber(1)
                .episodeCount(2)
                .build();

        TMDBGenreDTO genreOne = TMDBGenreDTO.builder()
                .id(1L)
                .name("Drama")
                .build();

        TMDBCreatorDTO creatorOne = TMDBCreatorDTO.builder()
                .id(101L)
                .name("Creator One")
                .imagePath("/path/to/image1.jpg")
                .build();

        showDTO.setSeasons(List.of(seasonDTO));
        showDTO.setGenres(List.of(genreOne));
        showDTO.setCreators(List.of(creatorOne));

        Season mockSeason = Season.builder()
                .id(7240L)
                .seasonName("Season 1")
                .seasonNumber(1)
                .episodeCount(2)
                .build();

        Genre mockGenre = Genre.builder()
                .id(1L)
                .name("Drama")
                .build();

        Creator mockCreator = Creator.builder()
                .id(101L)
                .name("Creator One")
                .imagePath("/path/to/image1.jpg")
                .build();

        when(seasonMapper.toEntity(seasonDTO)).thenReturn(mockSeason);
        when(genreMapper.toEntity(genreOne)).thenReturn(mockGenre);
        when(creatorMapper.toEntity(creatorOne)).thenReturn(mockCreator);

        Show show = showMapper.toEntity(showDTO);

        assertEquals(showDTO.getId(), show.getId());
        assertEquals(showDTO.getName(), show.getName());
        assertEquals(showDTO.getOverview(), show.getOverview());
        assertEquals(showDTO.getPopularity(), show.getPopularity());
        assertEquals(showDTO.getFirstAirDate(), show.getFirstAirDate());
        assertEquals(showDTO.getLastAirDate(), show.getLastAirDate());
        assertEquals(showDTO.getPosterPath(), show.getPosterPath());
        assertEquals(showDTO.getBackdropPath(), show.getBackdropPath());
        assertEquals(showDTO.getVoteAverage(), show.getVoteAverage());
        assertEquals(showDTO.getVoteCount(), show.getVoteCount());
        assertEquals(1, show.getSeasons().size());
        assertEquals(mockSeason, show.getSeasons().get(0));
        assertEquals(1, show.getGenres().size());
        assertEquals(mockGenre, show.getGenres().get(0));
        assertEquals(1, show.getCreators().size());
        assertEquals(mockCreator, show.getCreators().get(0));
    }
}

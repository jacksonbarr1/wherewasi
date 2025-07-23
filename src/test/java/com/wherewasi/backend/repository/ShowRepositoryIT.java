package com.wherewasi.backend.repository;

import com.wherewasi.backend.AbstractIT;
import com.wherewasi.backend.entity.Creator;
import com.wherewasi.backend.entity.Genre;
import com.wherewasi.backend.entity.Season;
import com.wherewasi.backend.entity.Show;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ShowRepositoryIT extends AbstractIT {

    @Autowired
    private ShowRepository showRepository;

    @Test
    @Transactional
    void whenShowIsSaved_thenFindByIdReturnsShow() {
        Show show = Show.builder()
                .id(2316L)
                .name("name")
                .overview("overview")
                .popularity(86.143f)
                .firstAirDate(LocalDate.of(2023, 1, 1))
                .lastAirDate(LocalDate.of(2025, 1, 1))
                .posterPath("posterPath")
                .backdropPath("backdropPath")
                .voteAverage(8.58f)
                .voteCount(100)
                .seasons(List.of(Season.builder().id(7240L).name("Season 1").seasonNumber(1).episodeCount(2).build()))
                .genres(List.of(Genre.builder().id(1L).name("Drama").build()))
                .creators(List.of(Creator.builder().id(101L).name("Creator One").imagePath("/path/to/image1.jpg").build()))
                .build();

        showRepository.save(show);

        Optional<Show> showOptional = showRepository.findById(2316L);

        assertThat(showOptional).isPresent();

        Show retrievedShow = showOptional.get();

        assertThat(show)
                .usingRecursiveComparison()
                .isEqualTo(retrievedShow);
    }

}

package com.wherewasi.backend.service;

import com.wherewasi.backend.AbstractTest;
import com.wherewasi.backend.client.TMDBApiClient;
import com.wherewasi.backend.dto.tmdb.TMDBShowDTO;
import com.wherewasi.backend.mapper.ShowMapper;
import com.wherewasi.backend.model.Show;
import com.wherewasi.backend.repository.ShowRepository;
import com.wherewasi.backend.util.TestDataLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TMDBIngestionServiceTest extends AbstractTest {

    private final TestDataLoader testDataLoader = new TestDataLoader();
    @Mock
    ShowRepository showRepository;
    @Mock
    private TMDBApiClient apiClient;
    @Mock
    private ShowMapper showMapper;
    @InjectMocks
    private TMDBIngestionService ingestionService;

    @Test
    void testIngestionProcess() throws Exception {
        TMDBShowDTO mockShowDTO = testDataLoader.loadJsonAsObject("example_tmdb_show_response.json",
                TMDBShowDTO.class);

        Show expectedShow = Show.builder()
                .id(2316L)
                .name("The Office")
                .overview("The everyday lives of office employees in the Scranton, Pennsylvania branch of the fictional Dunder Mifflin Paper Company.")
                .firstAirDate(Date.from(LocalDate.of(2005, 3, 24).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .lastAirDate(Date.from(LocalDate.of(2013, 5, 16).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .posterPath("/7DJKHzAi83BmQrWLrYYOqcoKfhR.jpg")
                .backdropPath("/bX6Sypdpk0r8YFdVPoc3yeyvSmm.jpg")
                .popularity(86.143f)
                .voteAverage(8.58f)
                .voteCount(4578)
                .build();

        when(apiClient.getTMDBShowDetails(2316L)).thenReturn(mockShowDTO);
        when(showRepository.save(any(Show.class))).thenReturn(expectedShow);
        when(showMapper.toEntity(any(TMDBShowDTO.class))).thenReturn(expectedShow);

        Show result = ingestionService.ingestCompleteShow(2316L);

        assertNotNull(result);
        assertEquals(expectedShow.getId(), result.getId());
        assertEquals(expectedShow.getName(), result.getName());
        assertEquals(expectedShow.getOverview(), result.getOverview());
        assertEquals(expectedShow.getFirstAirDate(), result.getFirstAirDate());
        assertEquals(expectedShow.getLastAirDate(), result.getLastAirDate());
        assertEquals(expectedShow.getPosterPath(), result.getPosterPath());
        assertEquals(expectedShow.getBackdropPath(), result.getBackdropPath());
        assertEquals(expectedShow.getPopularity(), result.getPopularity());
        assertEquals(expectedShow.getVoteAverage(), result.getVoteAverage());
        assertEquals(expectedShow.getVoteCount(), result.getVoteCount());


    }

}
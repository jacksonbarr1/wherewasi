package com.wherewasi.backend.service;

import com.wherewasi.backend.client.TMDBApiClient;
import com.wherewasi.backend.dto.tmdb.TMDBShowDTO;
import com.wherewasi.backend.mapper.ShowMapper;
import com.wherewasi.backend.entity.Show;
import com.wherewasi.backend.repository.ShowRepository;
import org.springframework.stereotype.Service;

@Service
public class TMDBIngestionService {

    private final TMDBApiClient apiClient;
    private final ShowRepository showRepository;
    private final ShowMapper showMapper;

    public TMDBIngestionService(TMDBApiClient apiClient, ShowRepository showRepository, ShowMapper showMapper) {
        this.apiClient = apiClient;
        this.showRepository = showRepository;
        this.showMapper = showMapper;
    }

    public Show ingestCompleteShow(Long showId) {
        // Get basic show metadata from `/details` and map to TMDBShowDTO
        TMDBShowDTO showDTO = apiClient.getTMDBShowDetails(showId);

        Show show = showMapper.toEntity(showDTO);
        return showRepository.save(show);

        // TODO: Use season data to call `/episode` and populate showDTO.seasons
    }
}

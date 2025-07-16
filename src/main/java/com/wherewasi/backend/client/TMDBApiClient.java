package com.wherewasi.backend.client;

import com.wherewasi.backend.dto.tmdb.TMDBShowDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class TMDBApiClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "https://api.themoviedb.org/3";
    private final String apiKey;

    public TMDBApiClient(@Value("${tmdb.api.key}") String apiKey) {
        this.apiKey = apiKey;
    }

    public TMDBShowDTO getTMDBShowDetails(Long showId) {
        String url = baseUrl + "/tv/" + showId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                TMDBShowDTO.class
        ).getBody();
    }
}

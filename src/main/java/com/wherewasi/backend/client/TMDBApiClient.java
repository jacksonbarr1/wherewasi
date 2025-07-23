package com.wherewasi.backend.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wherewasi.backend.dto.tmdb.TMDBShowDTO;
import com.wherewasi.backend.dto.tmdb.TMDBShowIdExportDTO;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;


@Component
public class TMDBApiClient {

    private static final Logger logger = LoggerFactory.getLogger(TMDBApiClient.class);

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private static final String TMDB_API_BASE_URL = "https://api.themoviedb.org/3";
    private static final String TMDB_FILE_EXPORT_URL = "http://files.tmdb.org/p/exports/";

    // TMDB has 50 req/s rate limit
    private final Bucket apiRequestBucket;

    public TMDBApiClient(RestClient tmdbRestClient, ObjectMapper objectMapper) {
        this.restClient = tmdbRestClient;
        this.objectMapper = objectMapper;

        Bandwidth limit = Bandwidth.simple(50, Duration.ofSeconds(1));

        this.apiRequestBucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    public Stream<TMDBShowIdExportDTO> downloadAndStreamShowIds(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM_dd_yyyy");
        String fileName = "tv_series_ids_" + localDate.format(formatter) + ".json.gz";
        String fileUrl = TMDB_FILE_EXPORT_URL + fileName;

        logger.info("Attempting to download TV series ID file export from: {}", fileUrl);

        try {
            byte[] response = restClient.get()
                    .uri(fileUrl)
                    .retrieve()
                    .body(byte[].class);

            if (response == null) {
                logger.error("Failed to download file: {}.", fileUrl);
                return Stream.empty();
            }

            ByteArrayInputStream bais = new ByteArrayInputStream(response);
            GZIPInputStream gis = new GZIPInputStream(bais);
            BufferedReader reader = new BufferedReader(new InputStreamReader(gis, StandardCharsets.UTF_8));

            return reader.lines().map(line ->{
                try {
                    return objectMapper.readValue(line, TMDBShowIdExportDTO.class);
                } catch (Exception e) {
                    logger.warn("Error parsing line to TMDBShowIdExportDTO: {} - {}", line, e.getMessage());
                    return null;
                }
            }).filter(java.util.Objects::nonNull)
                    .onClose(() -> {
                        try {
                            reader.close();
                            gis.close();
                            bais.close();
                        } catch (IOException e) {
                            logger.error("Error closing resources: {}", e.getMessage());
                        }
                    });
        } catch (Exception e) {
            logger.error("Error downloading or processing file: {} - {}", fileUrl, e.getMessage());
            return Stream.empty();
        }
    }

    public Optional<TMDBShowDTO> getTMDBShowDetails(Long showId) {
        URI uri = URI.create(String.format("%s/tv/%d", TMDB_API_BASE_URL, showId));
        return executeApiCall(uri, TMDBShowDTO.class, "TMDB Show Details", showId);
    }

    private <T> Optional<T> executeApiCall(URI uri, Class<T> responseType, String description, Object... identifiers) {
        try {
            apiRequestBucket.asBlocking().consume(1);

            logger.info("Making API call to {} for {} with identifiers: {}", uri, description, identifiers);
            T responseBody = restClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(HttpStatus.NOT_FOUND::equals, (req, res) -> {
                        throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Not Found for " + description + ": " + identifiers[0]);
                    })
                    .onStatus(HttpStatus.TOO_MANY_REQUESTS::equals, (req, res) -> {
                        throw new HttpClientErrorException(HttpStatus.TOO_MANY_REQUESTS, "Too Many Requests for " + description + ": " + identifiers[0]);
                    })
                    .body(responseType);

            if (responseBody != null) {
                return Optional.of(responseBody);
            } else {
                logger.warn("API call for {} returned null body. Identifiers: {}", description, identifiers);
                return Optional.empty();
            }
        } catch (HttpClientErrorException.NotFound e) {
            logger.warn("API call for {} returned 404 Not Found. Identifiers: {}", description, identifiers);
            return Optional.empty();
        } catch (HttpClientErrorException.TooManyRequests e) {
            logger.warn("API call for {} returned 429 Too Many Requests. Identifiers: {}", description, identifiers);
            return Optional.empty();
        } catch (RestClientException e) {
            logger.error("API call for {} failed with error: {}. Identifiers: {}", description, e.getMessage(), identifiers);
            return Optional.empty();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("API call for {} was interrupted while waiting for rate limit token. Identifiers: {}", description, identifiers);
            return Optional.empty();
        } catch (Exception e) {
            logger.error("API call for {} failed with unexpected error: {}. Identifiers: {}", description, e.getMessage(), identifiers);
            return Optional.empty();
        }
    }
}

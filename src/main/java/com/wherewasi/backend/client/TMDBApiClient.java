package com.wherewasi.backend.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wherewasi.backend.dto.tmdb.TMDBShowDTO;
import com.wherewasi.backend.dto.tmdb.TMDBShowIdExportDTO;
import org.mapstruct.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;


@Component
public class TMDBApiClient {

    private static final Logger logger = LoggerFactory.getLogger(TMDBApiClient.class);

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private static final String TMDB_API_BASE_URL = "https://api.themoviedb.org/3";
    private static final String TMDB_FILE_EXPORT_URL = "http://files.tmdb.org/p/exports/";

    public TMDBApiClient(RestClient restClient, ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
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


    public TMDBShowDTO getTMDBShowDetails(Long showId) {
        return restClient.get()
                .uri(TMDB_API_BASE_URL + "/tv/{id}", showId)
                .retrieve()
                .body(TMDBShowDTO.class);
    }
}

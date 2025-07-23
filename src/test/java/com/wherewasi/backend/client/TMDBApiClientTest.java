package com.wherewasi.backend.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wherewasi.backend.dto.tmdb.TMDBShowIdExportDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.GZIPOutputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TMDBApiClientTest {
    @Mock
    private RestClient restClient;
    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private RestClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private RestClient.ResponseSpec responseSpec;
    @Spy
    private ObjectMapper objectMapper;

    @InjectMocks
    private TMDBApiClient tmdbApiClient;

    private byte[] createGzippedByteArray(String content) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(bos)) {
            gzip.write(content.getBytes(StandardCharsets.UTF_8));
        }
        return bos.toByteArray();
    }

    @Test
    @DisplayName("Should successfully download, decompress, and stream parsed DTOs")
    void shouldDownloadDecompressAndStreamDTOs() throws IOException {
        LocalDate testDate = LocalDate.of(2025, 7, 21);
        String mockGzContent =
                        "{\"id\": 1, \"original_name\": \"Show A\", \"popularity\": 10.5}\n" +
                        "{\"id\": 2, \"original_name\": \"Show B\", \"popularity\": 20.0}\n" +
                        "{\"id\": 3, \"original_name\": \"Show C\", \"popularity\": 5.2}";

        byte[] gzippedBytes = createGzippedByteArray(mockGzContent);

        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(byte[].class)).thenReturn(gzippedBytes);

        Stream<TMDBShowIdExportDTO> resultStream = tmdbApiClient.downloadAndStreamShowIds(testDate);

        assertNotNull(resultStream);

        List<TMDBShowIdExportDTO> dtoList = resultStream.toList();

        assertFalse(dtoList.isEmpty());
        assertEquals(3, dtoList.size());

        assertEquals(1L, dtoList.get(0).getId());
        assertEquals("Show A", dtoList.get(0).getName());
        assertEquals(10.5f, dtoList.get(0).getPopularity(), 0.001f);

        assertEquals(2L, dtoList.get(1).getId());
        assertEquals("Show B", dtoList.get(1).getName());
        assertEquals(20.0f, dtoList.get(1).getPopularity(), 0.001f);

        assertEquals(3L, dtoList.get(2).getId());
        assertEquals("Show C", dtoList.get(2).getName());
        assertEquals(5.2f, dtoList.get(2).getPopularity(), 0.001f);
    }

}

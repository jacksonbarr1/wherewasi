package com.wherewasi.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class TestDataLoader {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String loadJsonAsString(String resourcePath) throws Exception {
        ClassPathResource resource = new ClassPathResource(resourcePath);
        return resource.getContentAsString(StandardCharsets.UTF_8);
    }

    public <T> T loadJsonAsObject(String resourcePath, Class<T> clazz) throws Exception {
        String json = loadJsonAsString(resourcePath);
        return objectMapper.readValue(json, clazz);
    }
}

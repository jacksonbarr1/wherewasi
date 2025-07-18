package com.wherewasi.backend.dto;

import lombok.Builder;

@Builder
public record UserDetailsDTO(String name, String lastName, String email) {
}
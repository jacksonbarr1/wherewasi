package com.wherewasi.backend.response;

import com.wherewasi.backend.dto.UserDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsResponse {
    private UserDetailsDTO userDetails;
}

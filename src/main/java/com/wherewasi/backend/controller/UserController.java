package com.wherewasi.backend.controller;

import com.wherewasi.backend.dto.UserDetailsDTO;
import com.wherewasi.backend.entity.User;
import com.wherewasi.backend.response.UserDetailsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping
    public ResponseEntity<UserDetailsResponse> getUserDetails(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        UserDetailsDTO userDetails = UserDetailsDTO.builder()
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();

        return ResponseEntity.ok(new UserDetailsResponse(userDetails));
    }

}

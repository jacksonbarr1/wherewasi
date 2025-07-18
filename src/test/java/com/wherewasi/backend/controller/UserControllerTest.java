package com.wherewasi.backend.controller;

import com.wherewasi.backend.AbstractTest;
import com.wherewasi.backend.entity.User;
import com.wherewasi.backend.enumeration.Role;
import com.wherewasi.backend.response.UserDetailsResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest extends AbstractTest {

    @InjectMocks
    private UserController userController;


    @Test
    void whenAuthenticatedUserRequestsDetails_thenReturnUserDetails() {
        Authentication authentication = mock(Authentication.class);
        User user = User.builder()
                .id(1L)
                .name("Test")
                .lastName("User")
                .email("test@wherewasi.com")
                .role(Role.USER)
                .build();

        when(authentication.getPrincipal()).thenReturn(user);

        ResponseEntity<UserDetailsResponse> response = userController.getUserDetails(authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test", response.getBody().getUserDetails().name());
        assertEquals("User", response.getBody().getUserDetails().lastName());
        assertEquals("test@wherewasi.com", response.getBody().getUserDetails().email());
    }
}

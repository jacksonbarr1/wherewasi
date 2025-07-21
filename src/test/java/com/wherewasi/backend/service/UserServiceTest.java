package com.wherewasi.backend.service;

import com.wherewasi.backend.AbstractTest;
import com.wherewasi.backend.dto.UserDetailsDTO;
import com.wherewasi.backend.entity.User;
import com.wherewasi.backend.enumeration.Role;
import com.wherewasi.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest extends AbstractTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void whenProvidedValidUserObject_thenProperUserDetailsDTOIsReturned() {

        UserDetailsDTO expectedUserDetailsDTO = new UserDetailsDTO("Test",
                "User", "test@wherewasi.com");

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(User.builder()
                .id(1L)
                .name("Test")
                .lastName("User")
                .email("test@wherewasi.com")
                .password("password")
                .role(Role.USER)
                .build()
        ));

        UserDetailsDTO userDetailsDTO = userService.getUserDetailsById(1L);

        assertEquals(userDetailsDTO.name(), expectedUserDetailsDTO.name());
        assertEquals(userDetailsDTO.lastName(), expectedUserDetailsDTO.lastName());
        assertEquals(userDetailsDTO.email(), expectedUserDetailsDTO.email());
    }

    @Test
    void whenProvidedInvalidUserId_thenExceptionIsThrown() {
        when(userRepository.findById(999L)).thenReturn(java.util.Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.getUserDetailsById(999L));
    }
}

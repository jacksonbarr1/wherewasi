package com.wherewasi.backend.repository;

import com.wherewasi.backend.AbstractIT;
import com.wherewasi.backend.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class UserRepositoryIT extends AbstractIT {

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenUserIsSaved_thenFindByEmailReturnsUser() {
        String testEmail = "test@wherewasi.com";

        User user = User.builder()
                .email(testEmail)
                .password("password")
                .name("test")
                .lastName("user")
                .build();

        userRepository.save(user);

        Optional<User> retrievedUser = userRepository.findByEmail(testEmail);

        assertTrue("User object should be present in Optional", retrievedUser.isPresent());
        assertEquals("Retrieved user ID should match saved user ID",
                user.getId(), retrievedUser.get().getId());
    }
}

package com.wherewasi.backend.service;

import com.wherewasi.backend.entity.User;
import com.wherewasi.backend.enumeration.Role;
import com.wherewasi.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultAdminUserInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DefaultAdminUserInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    @EventListener(ApplicationReadyEvent.class)
    public void createDefaultAdminUser() {
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User adminUser = User.builder()
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .role(Role.ADMIN)
                    .build();

            userRepository.save(adminUser);
            logger.info("Default admin user created with email: {}", adminEmail);
        } else {
            logger.info("Default admin user already exists with email: {}", adminEmail);
        }
    }

}

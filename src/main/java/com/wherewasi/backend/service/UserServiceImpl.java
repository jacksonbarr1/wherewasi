package com.wherewasi.backend.service;

import com.wherewasi.backend.dto.UserDetailsDTO;
import com.wherewasi.backend.entity.User;
import com.wherewasi.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetailsDTO getUserDetailsById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return UserDetailsDTO.builder()
                    .name(user.getName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .build();
        } else {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
    }
}

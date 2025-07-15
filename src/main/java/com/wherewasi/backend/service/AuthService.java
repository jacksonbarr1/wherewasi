package com.wherewasi.backend.service;

import com.wherewasi.backend.exception.AuthenticationFailedException;
import com.wherewasi.backend.exception.EmailTakenException;
import com.wherewasi.backend.model.Role;
import com.wherewasi.backend.model.User;
import com.wherewasi.backend.repository.UserRepository;
import com.wherewasi.backend.request.auth.AuthenticationRequest;
import com.wherewasi.backend.request.auth.RegisterRequest;
import com.wherewasi.backend.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws EmailTakenException {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailTakenException("Email is already taken");
        }

        var user = User.builder()
                .name(request.getName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
            System.out.println(user);
            userRepository.save(user);
        }

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws AuthenticationFailedException {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isEmpty() ||
            !passwordEncoder.matches(request.getPassword(), userOptional.get().getPassword())) {
            throw new AuthenticationFailedException("Invalid login");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}

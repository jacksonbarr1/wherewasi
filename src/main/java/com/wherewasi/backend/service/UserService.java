package com.wherewasi.backend.service;

import com.wherewasi.backend.dto.UserDetailsDTO;

public interface UserService {
    UserDetailsDTO getUserDetailsById(Long id);
}

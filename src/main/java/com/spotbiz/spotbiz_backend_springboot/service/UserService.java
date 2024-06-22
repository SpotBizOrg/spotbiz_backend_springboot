package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.UpdateUserRequestDto;
import com.spotbiz.spotbiz_backend_springboot.entity.AuthenticationResponse;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User register(User request);
    User update(UpdateUserRequestDto request);
    void delete(String username);
    AuthenticationResponse authenticate(User request);
    User getUserByEmail(String email);
    Page<User> getAllUsers(Pageable pageable);
}

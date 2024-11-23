package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessOwnerRegDto;
import com.spotbiz.spotbiz_backend_springboot.dto.CustomerAdminResponseDto;
import com.spotbiz.spotbiz_backend_springboot.dto.UpdateUserRequestDto;
import com.spotbiz.spotbiz_backend_springboot.entity.*;
import com.spotbiz.spotbiz_backend_springboot.mapper.UserMapper;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.PlayedGameRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.UserRepo;
import com.spotbiz.spotbiz_backend_springboot.service.JwtService;
import com.spotbiz.spotbiz_backend_springboot.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final BusinessRepo businessRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PlayedGameRepo playedGameRepo;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, PasswordEncoder encoder, JwtService jwtService, AuthenticationManager authenticationManager, BusinessRepo businessRepo, PlayedGameRepo playedGameRepo, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.businessRepo = businessRepo;
        this.playedGameRepo = playedGameRepo;
        this.userMapper = userMapper;
    }

    @Override
    public User register(User request) {
        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Customer already exists");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhoneNo(request.getPhoneNo());
        user.setStatus(request.getStatus());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        user = userRepo.save(user);

        String token = jwtService.generateToken(user);

        return user;
    }

    @Override
    public User registerBusinessOwner(BusinessOwnerRegDto dto) {
        if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhoneNo(dto.getPhoneNo());
        user.setStatus(dto.getStatus());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());

        user = userRepo.save(user);

        String token = jwtService.generateToken(user);

        // Save business details
        Business newBusiness = new Business(dto.getBusinessName(), dto.getBusinessRegNo(), "PENDING", user);
        newBusiness = businessRepo.save(newBusiness);

        return user;
    }

    @Override
    public User update(UpdateUserRequestDto request) {
        User user = userRepo.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getNewEmail() != null && !request.getNewEmail().equals(user.getUsername())) {
            if (userRepo.findByEmail(request.getNewEmail()).isPresent()) {
                throw new RuntimeException("Username already exists");
            }
            user.setEmail(request.getNewEmail());
        }

        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepo.findByEmail(request.getEmail()).isPresent()) {
                throw new RuntimeException("Email already exists");
            }
            user.setEmail(request.getEmail());
        }
        if (request.getPhoneNo() != null) {
            user.setPhoneNo(request.getPhoneNo());
        }
        if (request.getStatus() != null) {
            user.setStatus(request.getStatus());
        }
        if (request.getPassword() != null) {
            user.setPassword(encoder.encode(request.getPassword()));
        }
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }

        user = userRepo.save(user);

        return user;
    }

    @Override
    public void delete(String username) {
        User user = userRepo.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus(Status.DELETED);
    }

    @Override
    public AuthenticationResponse authenticate(User request) {
        User user = userRepo.findByEmail(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException ex) {
            throw new UsernameNotFoundException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token, user.getUserId(), user.getName(), user.getUsername(), user.getRole(), user.getStatus());
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    // New method to get all customers
    public List<CustomerAdminResponseDto> getAllCustomers() {
       try{
           List<User> customerList = userRepo.findAllByRole(Role.CUSTOMER);
           List<CustomerAdminResponseDto> dtoList = customerList.stream()
                   .map(userMapper::toCustomerAdminResponseDto)
                   .toList();

           for (CustomerAdminResponseDto dto : dtoList) {
//               int userId = 16;
               double score = playedGameRepo.getCustomerScore(dto.getUserId());
               dto.setScore(score);
           }

           return dtoList;
       } catch (Exception e) {
           throw new RuntimeException("Error occurred while fetching user data", e);
       }


    }


}

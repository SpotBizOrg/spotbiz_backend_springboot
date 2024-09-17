package com.spotbiz.spotbiz_backend_springboot.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse {
    private final String token;
    private final int user_id;
    private final String email;
    private final Role role;
    private final Status status;
    private final String name;

    public AuthenticationResponse(String token, int userId, String name, String email, Role role, Status status) {
        this.token = token;
        user_id = userId;
        this.email = email;
        this.role = role;
        this.status = status;
        this.name = name;
    }
}

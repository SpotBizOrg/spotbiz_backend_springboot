package com.spotbiz.spotbiz_backend_springboot.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse {
    private final String token;
    private final String email;
    private final Role role;

    public AuthenticationResponse(String token, Role role, String email){
        this.token = token;
        this.role = role;
        this.email = email;
    }

}

package com.spotbiz.spotbiz_backend_springboot.dto;

import com.spotbiz.spotbiz_backend_springboot.entity.Role;
import com.spotbiz.spotbiz_backend_springboot.entity.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequestDto {
    private String name;
    private String email;
    private String newEmail;
    private String phoneNo;
    private Status status;
    private String password;
    private Role role;
}

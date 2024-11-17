package com.spotbiz.spotbiz_backend_springboot.dto;

import com.spotbiz.spotbiz_backend_springboot.entity.Role;
import com.spotbiz.spotbiz_backend_springboot.entity.Status;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPointDto {
    private Integer userId;
    private String name;
    private String email;
    private String phoneNo;
    private Status status;
    private Role role;
    private int points; // Keep this as int or change to double if needed

    // Update constructor to accept double points
    public UserPointDto(Integer userId, String name, String email, String phoneNo, Status status, Role role, double points) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.status = status;
        this.role = role;
        this.points = (int) points; // Convert to int
    }
}

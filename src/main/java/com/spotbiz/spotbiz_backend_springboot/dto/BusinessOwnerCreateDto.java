package com.spotbiz.spotbiz_backend_springboot.dto;

import com.spotbiz.spotbiz_backend_springboot.entity.Role;
import com.spotbiz.spotbiz_backend_springboot.entity.Status;
import lombok.*;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessOwnerCreateDto {

    private String name;
    private String email;
    private String phoneNo;
    private String password;
    private Status status;
    private Role role;
    private String businessName;
    private String businessRegNo;
}

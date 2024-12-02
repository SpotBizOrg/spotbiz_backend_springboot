package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class UserDetailsDto {
        private int userId;
        private String userName;
        private float points;
}

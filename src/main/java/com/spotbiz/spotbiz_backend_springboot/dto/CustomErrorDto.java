package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorDto {

    private String message;
    private String error;


}

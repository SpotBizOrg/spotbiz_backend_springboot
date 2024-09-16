package com.spotbiz.spotbiz_backend_springboot.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class AdvertisementDto {
    private Integer adsId;
    private String data;
    private Boolean status;
}


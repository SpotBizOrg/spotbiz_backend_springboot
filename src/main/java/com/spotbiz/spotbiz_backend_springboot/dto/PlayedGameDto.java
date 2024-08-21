package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PlayedGameDto {
    private String gameId;
    private String userId;
    private Date dateTime;
    private float duration;
    private float points;
}

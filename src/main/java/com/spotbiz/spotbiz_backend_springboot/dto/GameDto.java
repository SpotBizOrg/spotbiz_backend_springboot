package com.spotbiz.spotbiz_backend_springboot.dto;

import com.spotbiz.spotbiz_backend_springboot.entity.GameType;
import lombok.Data;

@Data
public class GameDto {
    private int gameId;
    private String gameName;
    private GameType gameType;
    private String description;
    private String developer;
    private String gameUrl;
    private String imageUrl;
    private Boolean flag = false;
}

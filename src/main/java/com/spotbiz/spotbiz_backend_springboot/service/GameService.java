package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.GameDto;

public interface GameService {
    int insertGame(GameDto gameDto);
}

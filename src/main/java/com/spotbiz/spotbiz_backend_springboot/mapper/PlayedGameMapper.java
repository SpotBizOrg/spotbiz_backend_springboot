package com.spotbiz.spotbiz_backend_springboot.mapper;

import com.spotbiz.spotbiz_backend_springboot.dto.PlayedGameDto;
import com.spotbiz.spotbiz_backend_springboot.entity.PlayedGames;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlayedGameMapper {

    @Mappings({
            @Mapping(target = "dateTime", source = "dateTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    })
    PlayedGames toPlayedGames(PlayedGameDto playedGamesDto);
}

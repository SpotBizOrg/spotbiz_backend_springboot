package com.spotbiz.spotbiz_backend_springboot.config;

import com.spotbiz.spotbiz_backend_springboot.mapper.CouponMapper;
import com.spotbiz.spotbiz_backend_springboot.mapper.GameMapper;
import com.spotbiz.spotbiz_backend_springboot.mapper.PlayedGameMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

//    @Bean
//    public CustomerMapper customerMapper() {
//        return Mappers.getMapper(CustomerMapper.class);
//    }
//
//    @Bean
//    public ItemMapper itemMapper() {
//        return Mappers.getMapper(ItemMapper.class);
//    }
//
//    public CategoryMapper categoryMapper() {
//        return Mappers.getMapper(CategoryMapper.class);
//    }

    @Bean
    public PlayedGameMapper playedGameMapper() {
        return Mappers.getMapper(PlayedGameMapper.class);
    }

    @Bean
    public GameMapper gameMapper() {
        return Mappers.getMapper(GameMapper.class);
    }

    @Bean
    public CouponMapper couponMapper() {
        return Mappers.getMapper(CouponMapper.class);
    }

}

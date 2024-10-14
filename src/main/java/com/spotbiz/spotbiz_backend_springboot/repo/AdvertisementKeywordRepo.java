package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.AdvertisementKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
@EnableJpaRepositories
public interface AdvertisementKeywordRepo extends JpaRepository<AdvertisementKeyword, Integer> {
    @Query("SELECT ak FROM AdvertisementKeyword ak WHERE ak.Advertisement.adsId = :advertisementId")
    AdvertisementKeyword findByAdvertisementId(@Param("advertisementId") Integer advertisementId);
}

package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.dto.AdvertisementRecommendationDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Advertisement;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.util.List;

@EnableJpaRepositories
@Repository
public interface AdvertisementRepo extends JpaRepository<Advertisement, Integer> {
    List<Advertisement> findByBusinessBusinessId(Integer businessId);


    @Query(value = "SELECT a.ads_id, a.status, a.data, a.business_id, ak.tags FROM advertisement a " +
            "JOIN advertiesment_keyword ak ON a.ads_id = ak.advertisement_id " +
            "WHERE EXISTS ( " +
            "    SELECT 1 " +
            "    FROM jsonb_array_elements_text(ak.tags->'keywords') tag " +
            "    WHERE tag = ANY (:keywords))", nativeQuery = true)
//    @SqlResultSetMapping(name = "AdvertisementRecommendationMapping")
    List<Object[]> findByKeywords(@Param("keywords") String[] keywords);
}
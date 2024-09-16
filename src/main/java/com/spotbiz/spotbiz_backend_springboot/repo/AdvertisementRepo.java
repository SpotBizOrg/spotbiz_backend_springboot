package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepo extends JpaRepository<Advertisement, Integer> {
    List<Advertisement> findByBusinessBusinessId(Integer businessId);


//    @Query("SELECT u FROM Advertisement u WHERE u.business.businessId = :businessId AND u. LIKE %:status%")
//    List<Advertisement> findByBusinessBusinessIdAndTagsContaining(Integer businessId, String status);
}
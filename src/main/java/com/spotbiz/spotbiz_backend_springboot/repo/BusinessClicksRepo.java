package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.BusinessClicks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface BusinessClicksRepo extends JpaRepository<BusinessClicks, Integer> {

    @Query("SELECT bc FROM BusinessClicks bc WHERE bc.business.businessId = :businessId")
    Optional<BusinessClicks> findByBusinessId(Integer businessId);


}

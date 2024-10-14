package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.BusinessOpeningHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface BusinessOpeningHoursRepo extends JpaRepository<BusinessOpeningHours, Integer> {

    @Query("SELECT b FROM BusinessOpeningHours b WHERE b.business.businessId = :businessId")
    Optional<BusinessOpeningHours> findByBusinessId(Integer businessId);
}

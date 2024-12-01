package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessAccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface BusinessAccountDetailsRepo extends JpaRepository<BusinessAccountDetails, Integer> {
    BusinessAccountDetails getReferenceByBusiness(Business business);
}

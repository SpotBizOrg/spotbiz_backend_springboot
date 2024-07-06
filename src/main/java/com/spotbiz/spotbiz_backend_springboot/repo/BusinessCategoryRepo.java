package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.BusinessCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessCategoryRepo extends JpaRepository<BusinessCategory, Integer> {
    Optional<BusinessCategory> findById(Integer categoryId);
    Optional<BusinessCategory> findByBusiness(Business existingBusiness);


}

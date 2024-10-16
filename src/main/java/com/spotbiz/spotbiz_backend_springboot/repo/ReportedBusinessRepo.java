package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.ReportedBusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface ReportedBusinessRepo extends JpaRepository<ReportedBusiness, Integer> {

    List<ReportedBusiness> findReportedBusinessesByStatus(String status);

    Optional<ReportedBusiness> findByBusinessBusinessId(Integer businessId);
}

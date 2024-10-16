package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.ReportedBusiness;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportedBusinessRepo extends JpaRepository<ReportedBusiness, Integer> {

    List<ReportedBusiness> findReportedBusinessesByStatus(String status);
}

package com.spotbiz.spotbiz_backend_springboot.api;


import com.spotbiz.spotbiz_backend_springboot.dto.ReportedBusinessDto;
import com.spotbiz.spotbiz_backend_springboot.service.impl.ReportedBusinessServiceImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/reported-business")
public class ReportedBusinessController {

    private final ReportedBusinessServiceImpl reportedBusinessService;

    public ReportedBusinessController(ReportedBusinessServiceImpl reportedBusinessService) {
        this.reportedBusinessService = reportedBusinessService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveReportedBusiness(@RequestBody ReportedBusinessDto reportedBusinessDto) {
        try {
            return ResponseEntity.ok(reportedBusinessService.saveReportedBusiness(reportedBusinessDto));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to save reported business: " + e.getMessage());
        }
    }
}

package com.spotbiz.spotbiz_backend_springboot.api;


import com.spotbiz.spotbiz_backend_springboot.dto.ReportedBusinessDto;
import com.spotbiz.spotbiz_backend_springboot.service.impl.ReportedBusinessServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

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

    @GetMapping("/all")
    public ResponseEntity<?> getAllReportedBusinesses() {
        try {
            return ResponseEntity.ok(reportedBusinessService.getAllReportedBusiness());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to get reported businesses: " + e.getMessage());
        }
    }

    @PutMapping("/ban")
    public ResponseEntity<?> BanReportedBusiness(@RequestParam Integer reportId) {
        try {
            return ResponseEntity.ok(reportedBusinessService.BanReportedBusiness(reportId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to ban reported business: " + e.getMessage());
        }
    }

    @PutMapping("/delete")
    public ResponseEntity<?> RemoveReportRequest(@RequestParam Integer reportId) {
        try {
            return ResponseEntity.ok(reportedBusinessService.RemoveReportRequest(reportId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to remove report request: " + e.getMessage());
        }
    }
}

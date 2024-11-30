package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.entity.ReimbursementStatus;
import com.spotbiz.spotbiz_backend_springboot.entity.Reimbursements;
import java.util.List;

public interface ReimbursementService {
    int insertReimbursement(Reimbursements reimbursements);
    List<Reimbursements> getReimbursementByBusinessIdAndStatus(int businessId);
    List<Reimbursements> getAllReimbursementsByStatus();
    int changeStatus(int id, ReimbursementStatus reimbursementStatus);
}

package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.ReimburementResponseDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Reimbursements;
import com.spotbiz.spotbiz_backend_springboot.entity.ReimbursementStatus;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.ReimbursementRepo;
import com.spotbiz.spotbiz_backend_springboot.service.ReimbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReimbursementServiceImpl implements ReimbursementService {

    private final ReimbursementRepo reimbursementRepo;
    private final BusinessRepo businessRepo;

    @Autowired
    public ReimbursementServiceImpl(ReimbursementRepo reimbursementRepo, BusinessRepo businessRepo) {
        this.reimbursementRepo = reimbursementRepo;
        this.businessRepo = businessRepo;
    }

    @Override
    public int insertReimbursement(Reimbursements reimbursements) {
        try{
            return reimbursementRepo.save(reimbursements).getId();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ReimburementResponseDto> getReimbursementByBusinessIdAndStatus(int businessId) {
        try{
            List<Reimbursements> list = reimbursementRepo.getAllByBusinessAndStatus(businessRepo.findByBusinessId(businessId), ReimbursementStatus.PENDING);
            List<ReimburementResponseDto> responseList = new ArrayList<>();

            for ( Reimbursements reiburement: list) {
                ReimburementResponseDto response = new ReimburementResponseDto();
                response.setAmount(reiburement.getAmount());
                response.setDateTime(reiburement.getDateTime());
                response.setId(reiburement.getId());
                response.setBusinessName(reiburement.getBusiness().getName());
                response.setImages(reiburement.getImages());
                responseList.add(response);

            }
            return responseList;

        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ReimburementResponseDto> getAllReimbursementsByStatus() {
        try{
            List<Reimbursements> list = reimbursementRepo.findAllByStatus(ReimbursementStatus.PENDING);
            List<ReimburementResponseDto> responseList = new ArrayList<>();

            for ( Reimbursements reiburement: list) {
                ReimburementResponseDto response = new ReimburementResponseDto();
                response.setId(reiburement.getId());
                response.setAmount(reiburement.getAmount());
                response.setDateTime(reiburement.getDateTime());
                response.setImages(reiburement.getImages());
                response.setBusinessName(reiburement.getBusiness().getName());
                responseList.add(response);
            }
            return responseList;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int changeStatus(int id, ReimbursementStatus reimbursementStatus) {
        try{
            Reimbursements reimbursement = reimbursementRepo.getReferenceById(id);
            reimbursement.setStatus(reimbursementStatus);
            reimbursementRepo.save(reimbursement);
            return 1;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getTotalPaidReimbursements() {
        try {
            return reimbursementRepo.getTotalPaidReimbursements();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get the total reimbursements", e);
        }
    }
}

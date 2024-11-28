package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.PackageDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Package;
import com.spotbiz.spotbiz_backend_springboot.mapper.PackageMapper;
import com.spotbiz.spotbiz_backend_springboot.repo.PackageRepo;
import com.spotbiz.spotbiz_backend_springboot.service.PackageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PackageServiceImpl implements  PackageService {

    @Autowired
    private  PackageRepo packageRepository;

    @Autowired
    private PackageMapper packageMapper;

    @Override
    public List<PackageDto> getAllPackages() {
        try {
            // sort the result set based on the packageId
            List<Package> allPackages =  packageRepository.findAllOrderedByPackageIdAsc();

            // removing business for the efficiency
            List<PackageDto> optimizedPkgs = new ArrayList<>();
            for (Package pkg : allPackages) {
                PackageDto optimizedPkg = new PackageDto();
                optimizedPkg = packageMapper.toPackageDto(pkg);
                optimizedPkgs.add(optimizedPkg);
            }
            return optimizedPkgs;
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching all packages");
        }


    }

    @Override
    public Package savePackage(Package pkg) {
        return packageRepository.save(pkg);
    }

    @Override
    public  Package getPackageById(int packageId) {
        Package pkg = packageRepository.findByPackageId(packageId);
        if(pkg == null) {
            throw new RuntimeException("Package not found with id" + packageId);
        }
        return pkg;
    }
    @Override
    public boolean deletePackage(int packageId) {
        if (packageRepository.existsByPackageId(packageId)) {
            System.out.println("Delete package");
            packageRepository.deleteByPackageId(packageId);
            return true;
        }
        return false;
    }

    @Override
    public PackageDto getPackageByBusinessId(int businessId) {
        try {
            Package pkg =  packageRepository.findPackageByBusiness(businessId);
            return packageMapper.toPackageDto(pkg);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching package by business id");
        }
    }

    @Override
    public Package updatePackage(int packageId, Package pkg) {
        Package existingPackage = packageRepository.findByPackageId(packageId);
        if (existingPackage != null) {
            if (pkg.getFeature() != null & !Objects.equals(pkg.getFeature(), "")) {
                existingPackage.setFeature(pkg.getFeature());
            }

            if (pkg.getAdsPerWeek() > 0) {
                existingPackage.setAdsPerWeek(pkg.getAdsPerWeek());
            }

            if(pkg.getAnalytics() != null){
                existingPackage.setAnalytics(pkg.getAnalytics());
            }

            if(pkg.getFakeReviews() != null){
                existingPackage.setFakeReviews(pkg.getFakeReviews());
            }

            if(pkg.getRecommendation() != null){
                existingPackage.setRecommendation(pkg.getRecommendation());
            }

            if(pkg.getMessaging() != null){
                existingPackage.setMessaging(pkg.getMessaging());
            }

            if(pkg.getListing() != null & !Objects.equals(pkg.getFeature(), "")){
                existingPackage.setListing(pkg.getListing());
            }

            if(pkg.getPrice() > 0){
                existingPackage.setPrice(pkg.getPrice());
            }

            if(pkg.getIsActive() != null){
                existingPackage.setIsActive(pkg.getIsActive());
            }

            return packageRepository.save(existingPackage);
        } else {
            throw new RuntimeException("Package not found with id" + packageId);
        }
    }
}

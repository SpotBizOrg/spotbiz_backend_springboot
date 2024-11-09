package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.entity.Package;

import java.util.List;

public interface PackageService {

    List<Package> getAllPackages();
    Package savePackage(Package pkg);
    Package getPackageById(int packageId);
    Package updatePackage(int packageId, Package pkg);
    boolean deletePackage(int packageId);
}

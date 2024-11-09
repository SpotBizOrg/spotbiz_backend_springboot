package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.entity.Package;
import com.spotbiz.spotbiz_backend_springboot.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/packages")
public class PackageController {

    private final PackageService packageService;

    // Constructor injection (recommended over field injection)
    @Autowired
    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    // to create new packages
    @PostMapping("/add")
    public ResponseEntity<Package> createPackage(@RequestBody Package pkg) {
        try {
            Package savedPackage = packageService.savePackage(pkg); // Corrected `savedPackage` variable
            return ResponseEntity.ok(savedPackage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // retrieve all packages
    @GetMapping("/get_all")
    public ResponseEntity<List<Package>> getAllPackages() {
        try {
            List<Package> packages = packageService.getAllPackages();
            return ResponseEntity.ok(packages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // get a specific package by its ID
    @GetMapping("/get/{packageId}")
    public ResponseEntity<Package> getPackageById(@PathVariable int packageId) {
        Package pkg = packageService.getPackageById(packageId);
        return pkg != null ? ResponseEntity.ok(pkg) : ResponseEntity.notFound().build();
    }

    // update a specific package by its ID
    @PutMapping("/update/{packageId}")
    public ResponseEntity<Package> updatePackage(@PathVariable int packageId, @RequestBody Package pkg) {
        try {
            Package updatedPackage = packageService.updatePackage(packageId, pkg);
            return updatedPackage != null ? ResponseEntity.ok(updatedPackage) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // delete a package by its ID
    @DeleteMapping("/delete/{packageId}")
    public ResponseEntity<Void> deletePackage(@PathVariable int packageId) {
        try {
            boolean deleted = packageService.deletePackage(packageId); // Ensure `deletePackage` is defined in `PackageService`
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

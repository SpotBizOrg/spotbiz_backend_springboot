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
@CrossOrigin(origins = "http://localhost:5173")  // Allow requests from frontend on port 5173
public class PackageController {

    private final PackageService packageService;

    @Autowired
    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    // Create new package
    @PostMapping("/add")
    public ResponseEntity<Package> createPackage(@RequestBody Package pkg) {
        try {
            System.out.println("Received package: " + pkg);  // Log the received package
            Package savedPackage = packageService.savePackage(pkg);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPackage);
        } catch (Exception e) {
            System.err.println("Error creating package: " + e.getMessage()); // Log error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Retrieve all packages
    @GetMapping("/get_all")
    public ResponseEntity<List<Package>> getAllPackages() {
        try {
            List<Package> packages = packageService.getAllPackages();
            return ResponseEntity.ok(packages);
        } catch (Exception e) {
            System.err.println("Error fetching packages: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get a package by ID
    @GetMapping("/get/{packageId}")
    public ResponseEntity<Package> getPackageById(@PathVariable int packageId) {
        Package pkg = packageService.getPackageById(packageId);
        return pkg != null ? ResponseEntity.ok(pkg) : ResponseEntity.notFound().build();
    }

    // Update package
    @PutMapping("/update/{packageId}")
    public ResponseEntity<Package> updatePackage(@PathVariable int packageId, @RequestBody Package pkg) {
        try {
            Package updatedPackage = packageService.updatePackage(packageId, pkg);
            return updatedPackage != null ? ResponseEntity.ok(updatedPackage) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.err.println("Error updating package: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Delete package
    @DeleteMapping("/delete/{packageId}")
    public ResponseEntity<Void> deletePackage(@PathVariable int packageId) {
        try {
            boolean deleted = packageService.deletePackage(packageId);
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.err.println("Error deleting package: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

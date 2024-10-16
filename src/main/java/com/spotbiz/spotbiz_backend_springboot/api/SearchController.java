package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.service.impl.SearchServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/search/")
public class SearchController {

    private final BusinessRepo businessRepo;
    private final SearchServiceImpl searchService;

    public SearchController(BusinessRepo businessRepo, SearchServiceImpl searchService) {
        this.businessRepo = businessRepo;
        this.searchService = searchService;
    }

    // GET request method to search businesses
    @GetMapping("{searchText}")
    public ResponseEntity<?> searchBusinessesByGet(
            @PathVariable String searchText,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            String[] keywords = searchService.getKeywords(searchText).toArray(new String[0]);
            Pageable pageable = PageRequest.of(page, size);
            Page<Business> results = businessRepo.findByAnyTag(keywords, pageable);

            return ResponseEntity.ok(results.getContent()); // Ensure the result is returned correctly
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve businesses: " + ex.getMessage());
        }
    }

    // POST request method to search businesses
    @PostMapping("/post")
    public ResponseEntity<List<Business>> searchBusinessesByPost(
            @RequestBody String searchText,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            String[] keywords = searchService.getKeywords(searchText).toArray(new String[0]);
            Pageable pageable = PageRequest.of(page, size);
            Page<Business> results = businessRepo.findByAnyTag(keywords, pageable);
            return new ResponseEntity<>(results.getContent(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

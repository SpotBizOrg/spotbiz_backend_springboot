package com.spotbiz.spotbiz_backend_springboot.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotbiz.spotbiz_backend_springboot.dto.BusinessBoxDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.service.impl.SearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/search/")
public class SearchController {

  private final BusinessRepo businessRepo;
  private final SearchServiceImpl searchService;

  public SearchController(BusinessRepo businessRepo, SearchServiceImpl searchService) {
      this.businessRepo = businessRepo;
      this.searchService = searchService;
  }


  @GetMapping("{searchText}")
  public ResponseEntity<?> searchBusinesses(@PathVariable String searchText, @RequestParam int page, @RequestParam int size, @RequestParam(defaultValue = "0") int userId) {
     try{
         System.out.println("searchText"+searchText);

         String[] list = searchService.getKeywords(searchText, userId).toArray(new String[0]);

         // Use Page instead of List for paginated results
         Pageable pageable = PageRequest.of(page, size);
         Page<BusinessBoxDto> results = searchService.searchBusinesses(list, pageable);

         // Return the Page object directly, which includes pagination metadata
         return ResponseEntity.ok(results);

     } catch (Exception ex) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve businesses: " + ex.getMessage());
      }
  }

  @GetMapping("category/{categoryId}")
  public ResponseEntity<?> searchBusinessByCategory(@PathVariable String categoryId, @RequestParam int page, @RequestParam int size) {
      try {
          Integer categoryIdInt = Integer.parseInt(categoryId);
//            System.out.println(categoryId);
          Pageable pageable = PageRequest.of(page, size);
          Page<BusinessBoxDto> results = searchService.searchBusinessesByCategory(categoryIdInt, pageable);
          return ResponseEntity.ok(results);
      } catch (Exception ex) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve businesses: " + ex.getMessage());
      }
  }
  
  @PostMapping("/post")
  public ResponseEntity<List<Business>> searchBusinessesByPost(
          @RequestBody String searchText,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size,
          @RequestParam(defaultValue = "0") int userId){
      try {
          String[] keywords = searchService.getKeywords(searchText, userId).toArray(new String[0]);
          Pageable pageable = PageRequest.of(page, size);
          Page<Business> results = businessRepo.findByAnyTag(keywords, pageable);
          return new ResponseEntity<>(results.getContent(), HttpStatus.OK);
      } catch (Exception ex) {
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }
}

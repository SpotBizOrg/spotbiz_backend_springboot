package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.service.impl.SearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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


    @GetMapping("{searchText}")
    public ResponseEntity<?> searchBusinesses(@PathVariable String searchText, @RequestParam int page, @RequestParam int size) {
       try{
           System.out.println("searchText"+searchText);

           List<String> list =  searchService.getKeywords(searchText);

           List<Business> results = null;

           results = searchService.searchBusinesses(list, page, size);

//           for (String keyword : list) {
////               System.out.println("keyword"+keyword);
//               results.addAll(searchService.searchBusinesses(keyword, page, size));
//
//           }

           return ResponseEntity.ok(results);

       } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve businesses: " + ex.getMessage());
        }
    }
}

package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.service.SearchService;
import com.spotbiz.spotbiz_backend_springboot.service.impl.SearchServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/search/")
public class SearchController {


    @GetMapping("{searchText}")
    public ResponseEntity<?> searchBusinesses(@PathVariable String searchText) {
       try{
           System.out.println("searchText"+searchText);
           SearchServiceImpl searchService = new SearchServiceImpl();

           List<String> list =  searchService.getKeywords(searchText);
           return ResponseEntity.ok(list);
       } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve businesses: " + ex.getMessage());
        }
    }
}

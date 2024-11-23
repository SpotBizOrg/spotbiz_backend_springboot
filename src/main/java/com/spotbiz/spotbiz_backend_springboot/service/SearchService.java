package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessBoxDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchService {

    List<String> getKeywords(String searchText, int userId);

    Page<BusinessBoxDto> searchBusinesses(String[] keywords, Pageable pageable);

    Page<BusinessBoxDto> searchBusinessesByCategory(Integer categoryId, Pageable pageable);
}

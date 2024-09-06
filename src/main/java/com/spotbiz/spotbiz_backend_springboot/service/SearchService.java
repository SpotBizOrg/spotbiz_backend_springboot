package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.entity.Business;

import java.util.List;

public interface SearchService {

    List<String> getKeywords(String searchText);

    List<Business> searchBusinesses(List<String> keywords, int page, int size);
}

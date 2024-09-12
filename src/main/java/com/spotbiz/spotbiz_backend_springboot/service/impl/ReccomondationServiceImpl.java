package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.entity.SearchHistory;
import com.spotbiz.spotbiz_backend_springboot.repo.SearchHistoryRepo;
import com.spotbiz.spotbiz_backend_springboot.service.ReccomondationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReccomondationServiceImpl implements ReccomondationService {

    private final SearchHistoryRepo searchHistoryRepo;

    @Autowired
    public ReccomondationServiceImpl(SearchHistoryRepo searchHistoryRepo) {
        this.searchHistoryRepo = searchHistoryRepo;
    }

    @Override
    public SearchHistory saveReccomondation(SearchHistory newSearch) {
        try {
            newSearch = searchHistoryRepo.save(newSearch);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return newSearch;
    }
}

package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.entity.SearchHistory;

public interface ReccomondationService {

    public SearchHistory saveReccomondation(SearchHistory newSearch);
}

package com.spotbiz.spotbiz_backend_springboot.service;

import java.util.List;

public interface SearchService {

    List<String> getKeywords(String searchText);
}

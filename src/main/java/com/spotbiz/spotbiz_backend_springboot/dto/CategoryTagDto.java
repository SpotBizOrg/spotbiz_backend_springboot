package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.Data;
import java.util.List;

@Data
public class CategoryTagDto {
    private String categoryName;
    private List<String> tags;
}

package com.spotbiz.spotbiz_backend_springboot.dto;

import lombok.Data;
import java.util.List;

@Data
public class CategoryTagDto {
    private String categoryName;
    private List<String> tags;


    // Getters and setters
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
}


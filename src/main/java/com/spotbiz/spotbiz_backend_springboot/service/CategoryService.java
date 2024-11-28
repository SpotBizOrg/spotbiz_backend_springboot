package com.spotbiz.spotbiz_backend_springboot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.spotbiz.spotbiz_backend_springboot.dto.CategoryTagDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Category;
import com.spotbiz.spotbiz_backend_springboot.repo.CategoryRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category addTagToCategory(Integer categoryId, String tagName) throws IOException {
        if (tagName == null || tagName.trim().isEmpty()) {
            throw new RuntimeException("Tag name cannot be empty");
        }

        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            System.out.println(category.getTags());
            //category.addTag(tagName); // Call the method to add the tag

            List<String> tostring = getKeywordsFromJson(category.getTags());
            //System.out.println(tostring.get(0));
            tostring.add(tagName);

            JSONObject tolist = createJsonWithKeywords(tostring);
            //System.out.println(tolist.toString());
            category.setTags(tolist.toString());
            return categoryRepository.save(category);

        } else {
            throw new RuntimeException("Category not found with id: " + categoryId);
        }
    }


    // Method to add a new category with tags
//    public Category addCategoryWithTags(CategoryTagDto categoryTagDto) {
//        if (categoryTagDto.getCategoryName() == null || categoryTagDto.getCategoryName().trim().isEmpty()) {
//            throw new RuntimeException("Category name cannot be empty");
//        }
//
//        // Default to an empty list if no tags are provided
//        List<String> tagsList = categoryTagDto.getTags() != null ? categoryTagDto.getTags() : new ArrayList<>();
//
//        // Create the JSON structure for tags
//        JSONObject tagsJson = createJsonWithKeywords(tagsList);
//
//        Category category = new Category();
//        category.setCategoryName(categoryTagDto.getCategoryName());
//        category.setTags(tagsJson.toString()); // Store as JSON string
//
//        return categoryRepository.save(category);
//    }


    public CategoryTagDto convertToDto(Category category) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> tagsList;
        try {
            tagsList = objectMapper.readValue(category.getTags(), new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing tags from JSON", e);
        }

        CategoryTagDto dto = new CategoryTagDto();
        dto.setCategoryName(category.getCategoryName());
        dto.setTags(tagsList);
        return dto;
    }


    public Category addCategoryWithTags(CategoryTagDto categoryTagDto) {
        System.out.println("Category Name: " + categoryTagDto.getCategoryName());
        System.out.println("Tags: " + categoryTagDto.getTags());
        if (categoryTagDto.getCategoryName() == null || categoryTagDto.getCategoryName().trim().isEmpty()) {
            throw new RuntimeException("Category name cannot be empty");
        }

        // Default to an empty list if no tags are provided
        List<String> tagsList = categoryTagDto.getTags() != null ? categoryTagDto.getTags() : new ArrayList<>();

        // Serialize tagsList to JSON string
        String tagsJson;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            tagsJson = objectMapper.writeValueAsString(tagsList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing tags to JSON", e);
        }

        // Serialize tagsList into a JSON string in the "keywords" format
        String tagJson = createJsonString(tagsList);

        Category category = new Category();
        category.setCategoryName(categoryTagDto.getCategoryName());
        category.setTags(tagJson); // Save as a JSON string
//        category.setCategoryId(0);

        System.out.println(category.getCategoryId());

//        return null;
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        try {
            return categoryRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public JSONObject convertStringToJson(String jsonString) {
        return new JSONObject(jsonString);
    }

    /*
    public List<String> getKeywordFromJson(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray keywordsArray = json.object.getJSONArray(key: "keywords");

        List<String> keywordsList = new ArrayList<>();
        for (int i=0; i < keywordsArray.length(); i++) {
    */


    public List<String> getKeywordsFromJson(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray keywordsArray = jsonObject.getJSONArray("keywords");

        List<String> keywordsList = new ArrayList<>();
        for (int i = 0; i < keywordsArray.length(); i++) {
            keywordsList.add(keywordsArray.getString(i));
        }

        return keywordsList;

    }

    private String createJsonString(List<String> keywords) {
        try {
            Map<String, List<String>> keywordMap = new HashMap<>();
            keywordMap.put("keywords", keywords);

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(keywordMap); // Convert to JSON
        } catch (Exception e) {
            throw new RuntimeException("Error serializing tags to JSON", e);
        }
    }

    public JSONObject createJsonWithKeywords(List<String> keywords) {
        JSONObject jsonObject = new JSONObject();
        JSONArray keywordsArray = new JSONArray(keywords);  // Directly convert List<String> to JSONArray

        jsonObject.put("keywords", keywordsArray);
        return jsonObject;
    }



}




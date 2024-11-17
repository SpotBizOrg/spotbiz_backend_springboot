package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.entity.Category;
import com.spotbiz.spotbiz_backend_springboot.repo.CategoryRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<String> getKeywordsFromJson(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray keywordsArray = jsonObject.getJSONArray("keywords");

        List<String> keywordsList = new ArrayList<>();
        for (int i = 0; i < keywordsArray.length(); i++) {
            keywordsList.add(keywordsArray.getString(i));
        }

        return keywordsList;

    }

    public JSONObject createJsonWithKeywords(List<String> keywords) {
        JSONObject jsonObject = new JSONObject();
        JSONArray keywordsArray = new JSONArray(keywords);  // Directly convert List<String> to JSONArray

        jsonObject.put("keywords", keywordsArray);
        return jsonObject;
    }
}

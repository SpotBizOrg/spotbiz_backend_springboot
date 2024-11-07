//package com.spotbiz.spotbiz_backend_springboot.entity;
//
//import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.Type;
//
//import java.util.List;
//import java.util.Map;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "category")
//public class Category {
//   @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "category_id")
//    private Integer categoryId;
//
//    @Column(name = "category_name")
//    private String categoryName;
//
//    @Type(JsonBinaryType.class)
//    @Column(columnDefinition = "jsonb")
//    private String tags;
//
//}


package com.spotbiz.spotbiz_backend_springboot.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "category_id")
 private Integer categoryId;

 @Column(name = "category_name")
 private String categoryName;

 @Type(JsonBinaryType.class)
 @Column(columnDefinition = "jsonb")
 private String tags;

 // Method to add a tag
// public void addTag(String tagName) throws IOException {
//  ObjectMapper objectMapper = new ObjectMapper();
//  List<String> tagsList = new ArrayList<>();
//
//  if (this.tags != null && !this.tags.isEmpty()) {
//   try {
//    tagsList = objectMapper.readValue(this.tags, new TypeReference<List<String>>() {});
//   } catch (IOException e) {
//    System.err.println("Error parsing existing tags JSON: " + e.getMessage());
//    throw e;
//   }
//  }
//  tagsList.add(tagName);
//  this.tags = objectMapper.writeValueAsString(tagsList);
// }


 public void addTag(String tagName) throws IOException {
  ObjectMapper objectMapper = new ObjectMapper();
  List<String> tagsList;

  // Parse the existing tags JSON if present
  if (this.tags == null || this.tags.isEmpty()) {
   tagsList = new ArrayList<>();
  } else {
   try {
    tagsList = objectMapper.readValue(this.tags, new TypeReference<List<String>>() {});
   } catch (IOException e) {
    System.err.println("Error parsing existing tags JSON: " + e.getMessage());
    throw e;
   }
  }

  // Add the new tag to the list
  tagsList.add(tagName);

  // Convert the updated list back to a JSON string
  this.tags = objectMapper.writeValueAsString(tagsList);
 }

}

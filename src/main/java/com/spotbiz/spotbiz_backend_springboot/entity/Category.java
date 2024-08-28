package com.spotbiz.spotbiz_backend_springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

//    @ElementCollection
//    @Column(name = "tags")
//    private List<String> tags; // updating this to a list

      @Column(name = "tags")
      private  String tags;

//    @Convert(converter = JsonConverter.class)
//    private List<String> tags;

}

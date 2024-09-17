package com.spotbiz.spotbiz_backend_springboot.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="user_search_history")
public class SearchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_id")
    private Integer rowId;

    @Column(name = "user_id")
    private Integer userId;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private String generatedKeywords;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ")
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "updated_at", columnDefinition = "TIMESTAMPTZ")
    private LocalDateTime updatedAt = LocalDateTime.now();


    public SearchHistory() {
    }
    public SearchHistory(Integer userId, String generatedKeywords, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.generatedKeywords = generatedKeywords;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public SearchHistory(Integer userId, String generatedKeywords) {
        this.userId = userId;
        this.generatedKeywords = generatedKeywords;
    }
}

package com.spotbiz.spotbiz_backend_springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_id")
    private Integer reviewId;

    @Column(nullable = false)
    private String title;

    @Column(name="description", nullable = false, length = 500)
    private String description;

    private Integer rating;

    @Column(name="date", nullable = false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    private String status;


    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", date=" + date +
                ", user=" + (user != null ? user.getUserId() : "null") +
                ", business=" + (business != null ? business.getBusinessId() : "null") +
                '}';
    }

}

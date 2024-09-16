package com.spotbiz.spotbiz_backend_springboot.dto;

import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDto {
    private String title;
    private String description;
    private LocalDateTime date;
    private Integer userId;
    private Integer businessId;
    private Integer rating;


    @Override
    public String toString() {
        return "ReviewRequestDto{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", userId=" + userId +
                ", businessId=" + businessId +
                ", rating=" + rating +
                '}';
    }

}

package com.spotbiz.spotbiz_backend_springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subscribe")
public class Subscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="subscribe_id")
    private int subscribeId;

    @Column(name="date_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name="business_id", referencedColumnName = "business_id")
    private Business business;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private User user;

}

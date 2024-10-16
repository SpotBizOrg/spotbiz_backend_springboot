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
@Table(name = "notification_token")
public class NotificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notification_token_id")
    private int notificationTokenId;

    @Column(name="date_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

    @Column(name="token")
    private String token;

    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private User user;
}

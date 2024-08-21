package com.spotbiz.spotbiz_backend_springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "played_games")
public class PlayedGames {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="play_id")
    private Integer playId;

    @Column(name="user_id")
    private Integer userId;

    @Column(name="game_id")
    private Integer gameId;

    @Column(name="date_time")
    private Date dateTime;

    @Column(name="duration")
    private float duration;

    @Column(name="points")
    private float points;

}

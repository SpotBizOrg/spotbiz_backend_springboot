package com.spotbiz.spotbiz_backend_springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="game_id")
    private int gameId;

    @Column(name="game_name")
    private String gameName;

    @Column(name="game_type")
    @Enumerated(value=EnumType.STRING)
    private GameType gameType;

    @Column(name="description")
    private String description;

    @Column(name="developer")
    private String developer;

    @Column(name="game_url")
    private String gameUrl;

    @Column(name="flag")
    private Boolean flag = false;
}

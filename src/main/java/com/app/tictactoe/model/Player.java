package com.app.tictactoe.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "x")
    private Set<Game> x = new HashSet<>();

    @OneToMany(mappedBy = "o")
    private Set<Game> o = new HashSet<>();

    @OneToOne(mappedBy = "player")
    private PreGame preGame;
}

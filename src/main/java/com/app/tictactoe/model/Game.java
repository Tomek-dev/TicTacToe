package com.app.tictactoe.model;

import com.app.tictactoe.other.enums.Win;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne
    private Player x;

    @ManyToOne
    private Player o;

    @OneToOne(mappedBy = "game")
    private Stop stop;

    private Win winner;

    public Game() {
        date = LocalDate.now();
    }
}

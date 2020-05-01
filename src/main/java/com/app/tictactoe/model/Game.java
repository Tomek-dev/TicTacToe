package com.app.tictactoe.model;

import com.app.tictactoe.other.enums.Process;
import com.app.tictactoe.other.enums.Win;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private Process process;

    private Win winner;

    @ManyToOne
    private Player x;

    @ManyToOne
    private Player o;

    @OneToOne(mappedBy = "game")
    private Disconnect disconnect;

    @OneToMany(mappedBy = "game")
    private Set<Field> fields = new HashSet<>();

    public Game() {
        date = LocalDate.now();
    }
}

package com.app.tictactoe.model;

import com.app.tictactoe.other.enums.Mark;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Getter
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer row;

    private Integer col;

    private Mark mark;

    @ManyToOne
    private Game game;
}

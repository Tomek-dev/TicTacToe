package com.app.tictactoe.other.dto;

import com.app.tictactoe.other.enums.Win;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileGameDto {

    private String date;
    private String xUserUsername;
    private String oUserUsername;
    private String winner;

    public void setWinner(Win winner) {
        if(winner.equals(Win.DRAW)){
            return;
        }
        this.winner = (winner.equals(Win.X) || winner.equals(Win.O_LEFT) ? xUserUsername : oUserUsername);
    }
}

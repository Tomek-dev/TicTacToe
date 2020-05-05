package com.app.tictactoe.other.builder;

import com.app.tictactoe.model.Field;
import com.app.tictactoe.model.Game;
import com.app.tictactoe.model.Player;
import com.app.tictactoe.model.Disconnect;
import com.app.tictactoe.other.enums.Process;
import com.app.tictactoe.other.enums.Win;

import java.time.LocalDate;
import java.util.Set;

public class GameBuilder {

    private Game game = new Game();

    public static GameBuilder builder(){
        return new GameBuilder();
    }

    public GameBuilder id(Long id){
        game.setId(id);
        return this;
    }

    public GameBuilder x(Player x){
        game.setX(x);
        return this;
    }

    public GameBuilder o(Player o){
        game.setO(o);
        return this;
    }

    public GameBuilder disconnect(Disconnect disconnect){
        game.setDisconnect(disconnect);
        return this;
    }

    public GameBuilder winner(Win winner){
        game.setWinner(winner);
        return this;
    }

    public GameBuilder process(Process process){
        game.setProcess(process);
        return this;
    }

    public GameBuilder fields(Set<Field> fields){
        game.setFields(fields);
        return this;
    }

    public GameBuilder date(LocalDate date){
        game.setDate(date);
        return this;
    }

    public Game build(){
        return game;
    }
}

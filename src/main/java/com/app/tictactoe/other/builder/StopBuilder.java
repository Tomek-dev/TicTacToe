package com.app.tictactoe.other.builder;

import com.app.tictactoe.model.Field;
import com.app.tictactoe.model.Game;
import com.app.tictactoe.model.Stop;

import java.util.Set;

public class StopBuilder {

    private Stop stop = new Stop();

    public static StopBuilder builder(){
        return new StopBuilder();
    }

    public StopBuilder id(Long id){
        stop.setId(id);
        return this;
    }

    public StopBuilder game(Game game){
        stop.setGame(game);
        return this;
    }

    public StopBuilder fields(Set<Field> fields){
        stop.setFields(fields);
        return this;
    }

    public Stop build(){
        return stop;
    }
}

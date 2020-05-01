package com.app.tictactoe.other.builder;

import com.app.tictactoe.model.Game;
import com.app.tictactoe.model.Disconnect;
import com.app.tictactoe.other.enums.Leave;

public class DisconnectBuilder {

    private Disconnect disconnect = new Disconnect();

    public static DisconnectBuilder builder(){
        return new DisconnectBuilder();
    }

    public DisconnectBuilder id(Long id){
        disconnect.setId(id);
        return this;
    }

    public DisconnectBuilder game(Game game){
        disconnect.setGame(game);
        return this;
    }

    public DisconnectBuilder leave(Leave leave){
        disconnect.setLeave(leave);
        return this;
    }

    public Disconnect build(){
        return disconnect;
    }
}

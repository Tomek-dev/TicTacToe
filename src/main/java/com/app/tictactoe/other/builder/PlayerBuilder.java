package com.app.tictactoe.other.builder;

import com.app.tictactoe.model.Game;
import com.app.tictactoe.model.Player;
import com.app.tictactoe.model.User;

import java.util.Set;

public class PlayerBuilder {

    private Player player = new Player();

    public static PlayerBuilder builder(){
        return new PlayerBuilder();
    }

    public PlayerBuilder id(Long id){
        player.setId(id);
        return this;
    }

    public PlayerBuilder user(User user){
        player.setUser(user);
        return this;
    }

    public PlayerBuilder x(Set<Game> x){
        player.setX(x);
        return this;
    }

    public PlayerBuilder o(Set<Game> o){
        player.setO(o);
        return this;
    }
}

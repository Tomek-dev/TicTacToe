package com.app.tictactoe;

import com.app.tictactoe.dao.PlayerDao;
import com.app.tictactoe.dao.UserDao;
import com.app.tictactoe.model.Player;
import com.app.tictactoe.model.User;
import com.app.tictactoe.other.builder.PlayerBuilder;
import com.app.tictactoe.other.builder.UserBuilder;
import com.app.tictactoe.other.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

@Component
public class Start {

    private PasswordEncoder passwordEncoder;
    private UserDao userDao;
    private PlayerDao playerDao;

    @Autowired
    public Start(PasswordEncoder passwordEncoder, UserDao userDao, PlayerDao playerDao) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.playerDao = playerDao;
        init();
    }

    private void init(){
        User[] users = new User[2];
        Player[] players = new Player[2];
        users[0] = UserBuilder.builder()
                .username("simba")
                .email("simba@email.email")
                .roles(Collections.singleton(Role.User))
                .password(passwordEncoder.encode("password"))
                .build();
        players[0] = PlayerBuilder.builder()
                .user(users[0])
                .build();
        users[1] = UserBuilder.builder()
                .username("timon")
                .email("timon@email.email")
                .roles(Collections.singleton(Role.User))
                .password(passwordEncoder.encode("password"))
                .build();
        players[1] = PlayerBuilder.builder()
                .user(users[1])
                .build();
        userDao.saveAll(Arrays.asList(users));
        playerDao.saveAll(Arrays.asList(players));
    }
}

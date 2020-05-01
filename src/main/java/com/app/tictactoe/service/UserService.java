package com.app.tictactoe.service;

import com.app.tictactoe.dao.PlayerDao;
import com.app.tictactoe.dao.UserDao;
import com.app.tictactoe.model.Player;
import com.app.tictactoe.model.User;
import com.app.tictactoe.other.builder.PlayerBuilder;
import com.app.tictactoe.other.builder.UserBuilder;
import com.app.tictactoe.other.dto.SignUpDto;
import com.app.tictactoe.other.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    private UserDao userDao;
    private PlayerDao playerDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDao userDao, PlayerDao playerDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.playerDao = playerDao;
        this.passwordEncoder = passwordEncoder;
    }

    public void create(SignUpDto signUp){
        User user = UserBuilder.builder()
                .username(signUp.getUsername())
                .email(signUp.getEmail())
                .password(passwordEncoder.encode(signUp.getPassword()))
                .roles(Collections.singleton(Role.User))
                .build();
        Player player = PlayerBuilder.builder()
                .user(user)
                .build();
        userDao.save(user);
        playerDao.save(player);
    }
}

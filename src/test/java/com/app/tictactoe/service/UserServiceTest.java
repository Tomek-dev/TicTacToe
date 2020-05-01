package com.app.tictactoe.service;

import com.app.tictactoe.dao.PlayerDao;
import com.app.tictactoe.dao.UserDao;
import com.app.tictactoe.model.User;
import com.app.tictactoe.other.dto.SignUpDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private PlayerDao playerDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void shouldCreate(){
        //given
        SignUpDto signUp = new SignUpDto();
        signUp.setPassword("password");
        signUp.setEmail("email");
        signUp.setUsername("username");
        final User[] saved = new User[1];
        given(userDao.save(Mockito.any())).willAnswer(invocationOnMock -> saved[0] = (User) invocationOnMock.getArguments()[0]);
        given(passwordEncoder.encode(Mockito.anyString())).willAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        //when
        userService.create(signUp);

        //then
        verify(playerDao).save(any());

        assertEquals("username", saved[0].getUsername());
        assertEquals("email", saved[0].getEmail());
        assertEquals("password", saved[0].getPassword());
    }
}
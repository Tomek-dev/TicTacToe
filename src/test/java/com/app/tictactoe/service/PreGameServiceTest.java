package com.app.tictactoe.service;

import com.app.tictactoe.dao.PreGameDao;
import com.app.tictactoe.model.Player;
import com.app.tictactoe.model.PreGame;
import com.app.tictactoe.model.User;
import com.app.tictactoe.other.builder.PlayerBuilder;
import com.app.tictactoe.other.builder.UserBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class PreGameServiceTest {

    @Mock
    private PreGameDao preGameDao;

    @InjectMocks
    private PreGameService preGameService;

    @Test
    public void shouldCreate(){
        //given
        User user = UserBuilder.builder()
                .username("player")
                .build();
        Player player = PlayerBuilder.builder()
                .user(user)
                .build();
        final PreGame[] saved = new PreGame[1];
        given(preGameDao.save(Mockito.any())).willAnswer(invocationOnMock -> saved[0] = (PreGame) invocationOnMock.getArguments()[0]);
        given(preGameDao.existsByPlayer(Mockito.any())).willReturn(false);

        //when
        preGameService.create(player);

        //then
        assertEquals("player", saved[0].getPlayer().getUser().getUsername());
    }

    @Test
    public void shouldReturnTrue(){
        //given
        given(preGameDao.count()).willReturn(4L);

        //then
        assertTrue(preGameService.existsAny());
    }

    @Test
    public void shouldReturnFalse(){
        //given
        given(preGameDao.count()).willReturn(0L);

        //then
        assertFalse(preGameService.existsAny());
    }
}
package com.app.tictactoe.service;

import com.app.tictactoe.dao.GameDao;
import com.app.tictactoe.dao.StopDao;
import com.app.tictactoe.model.Game;
import com.app.tictactoe.model.Stop;
import com.app.tictactoe.other.builder.GameBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class StopServiceTest {

    @Mock
    private StopDao stopDao;

    @Mock
    private GameDao gameDao;

    @InjectMocks
    private StopService stopService;

    @Test
    public void shouldCreate(){
        //given
        Game game = GameBuilder.builder()
                .build();
        given(gameDao.findById(Mockito.any())).willReturn(Optional.of(game));
        given(stopDao.save(Mockito.any())).willAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        //when
        Stop stop = stopService.create(4L);

        //then
        assertEquals(game, stop.getGame());
    }
}
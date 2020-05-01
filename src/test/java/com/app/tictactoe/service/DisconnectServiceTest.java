package com.app.tictactoe.service;

import com.app.tictactoe.dao.GameDao;
import com.app.tictactoe.dao.DisconnectDao;
import com.app.tictactoe.model.Game;
import com.app.tictactoe.model.Disconnect;
import com.app.tictactoe.other.builder.DisconnectBuilder;
import com.app.tictactoe.other.builder.GameBuilder;
import com.app.tictactoe.other.enums.Leave;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DisconnectServiceTest {

    @Mock
    private DisconnectDao disconnectDao;

    @Mock
    private GameDao gameDao;

    @InjectMocks
    private DisconnectService disconnectService;

    @Test
    public void shouldCreateWithDisconnectNull(){
        //given
        Game game = GameBuilder.builder()
                .build();
        given(gameDao.findById(Mockito.any())).willReturn(Optional.of(game));
        final Disconnect[] saved = new Disconnect[1];
        given(disconnectDao.save(Mockito.any())).willAnswer(invocationOnMock -> saved[0] = (Disconnect) invocationOnMock.getArguments()[0]);

        //when
        disconnectService.create(4L, Leave.O_LEFT);

        //then
        assertEquals(game, saved[0].getGame());
        assertEquals(Leave.O_LEFT, saved[0].getLeave());
    }

    @Test
    public void shouldCreateWithDisconnectNotNull(){
        //given
        Game game = GameBuilder.builder()
                .build();
        Disconnect disconnect = DisconnectBuilder.builder()
                .leave(Leave.O_LEFT)
                .game(game)
                .build();
        game.setDisconnect(disconnect);
        given(gameDao.findById(Mockito.any())).willReturn(Optional.of(game));
        final Game[] saved = new Game[1];
        given(gameDao.save(Mockito.any())).willAnswer(invocationOnMock -> saved[0] = (Game) invocationOnMock.getArguments()[0]);

        //when
        disconnectService.create(4L, Leave.X_LEFT);

        //then
        assertEquals(Leave.BOTH_LEFT, saved[0].getDisconnect().getLeave());
    }

    @Test
    public void shouldReturnFalse(){
        //given
        Disconnect disconnect = DisconnectBuilder.builder()
                .leave(Leave.O_LEFT)
                .build();
        given(disconnectDao.findByGameId(Mockito.any())).willReturn(Optional.of(disconnect));

        //when
        Boolean condition = disconnectService.reconnect(4L, Leave.O_LEFT);

        //then
        verify(disconnectDao).delete(disconnect);

        assertFalse(condition);
    }

    @Test
    public void shouldReturnTrue(){
        //given
        Disconnect disconnect = DisconnectBuilder.builder()
                .leave(Leave.BOTH_LEFT)
                .build();
        given(disconnectDao.findByGameId(Mockito.any())).willReturn(Optional.of(disconnect));
        final Disconnect[] saved = new Disconnect[1];
        given(disconnectDao.save(Mockito.any())).willAnswer(invocationOnMock -> saved[0] = (Disconnect) invocationOnMock.getArguments()[0]);

        //when
        Boolean condition = disconnectService.reconnect(4L, Leave.O_LEFT);

        //then
        verify(disconnectDao).save(disconnect);

        assertEquals(Leave.O_LEFT, saved[0].getLeave());
        assertTrue(condition);
    }
}
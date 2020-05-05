package com.app.tictactoe.service;

import com.app.tictactoe.dao.GameDao;
import com.app.tictactoe.dao.PreGameDao;
import com.app.tictactoe.model.Game;
import com.app.tictactoe.model.Player;
import com.app.tictactoe.model.PreGame;
import com.app.tictactoe.model.User;
import com.app.tictactoe.other.builder.GameBuilder;
import com.app.tictactoe.other.builder.PlayerBuilder;
import com.app.tictactoe.other.builder.UserBuilder;
import com.app.tictactoe.other.dto.GameDto;
import com.app.tictactoe.other.dto.ProfileGameDto;
import com.app.tictactoe.other.enums.Process;
import com.app.tictactoe.other.enums.Win;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

    @Mock
    private GameDao gameDao;

    @Mock
    private PreGameDao preGameDao;

    @InjectMocks
    private GameService gameService;

    private Player x;
    private Player o;

    @Before
    public void init(){
        User userO = UserBuilder.builder()
                .username("o")
                .build();
        o = PlayerBuilder.builder()
                .user(userO)
                .build();
        User userX = UserBuilder.builder()
                .username("x")
                .build();
        x = PlayerBuilder.builder()
                .user(userX)
                .build();
    }

    @Test
    public void shouldReturnGameDto(){
        //given
        Game game = GameBuilder.builder()
                .x(x)
                .o(o)
                .build();
        given(gameDao.findById(Mockito.any())).willReturn(Optional.of(game));

        //when
        GameDto dto = gameService.findById(4L);

        //then
        assertEquals("x", dto.getXUserUsername());
        assertEquals("o", dto.getOUserUsername());
    }

    @Test
    public void shouldReturnProfileGameDto(){
        //given
        Game game = GameBuilder.builder()
                .x(x)
                .o(o)
                .winner(Win.O)
                .date(LocalDate.parse("2019-01-21"))
                .build();
        given(gameDao.findByXOrO(Mockito.any(), Mockito.any())).willReturn(Collections.singletonList(game));

        //when
        List<ProfileGameDto> list = gameService.findByPlayer(x);

        //then
        assertEquals("x", list.get(0).getXUserUsername());
        assertEquals("o", list.get(0).getOUserUsername());
        assertEquals("o", list.get(0).getWinner());
        assertEquals("2019-01-21", list.get(0).getDate());
    }

    @Test
    public void shouldCreate(){
        //given
        PreGame preGame = PreGame.builder()
                .player(x)
                .build();
        given(preGameDao.findTop1ByOrderByIdAsc()).willReturn(Optional.of(preGame));
        given(gameDao.save(Mockito.any())).willAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        //when
        Game game = gameService.create(o);

        assertEquals(o, game.getO());
        assertEquals(x, game.getX());
        assertEquals(Process.DURING , game.getProcess());
    }
}
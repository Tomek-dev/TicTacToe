package com.app.tictactoe.service;

import com.app.tictactoe.dao.FieldDao;
import com.app.tictactoe.dao.GameDao;
import com.app.tictactoe.model.Field;
import com.app.tictactoe.model.Game;
import com.app.tictactoe.other.dto.FieldDto;
import com.app.tictactoe.other.enums.Mark;
import com.app.tictactoe.other.websocket.GameMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class FieldServiceTest {

    @Mock
    private FieldDao fieldDao;

    @Mock
    private GameDao gameDao;

    @InjectMocks
    private FieldService fieldService;

    @Test
    public void shouldCreate(){
        //given
        GameMessage message = new GameMessage();
        message.setCol(4);
        message.setRow(4);
        message.setMark(Mark.X);
        final Field[] saved = new Field[1];
        given(gameDao.findById(Mockito.any())).willReturn(Optional.of(new Game()));
        given(fieldDao.save(Mockito.any())).willAnswer(invocationOnMock -> saved[0] = (Field) invocationOnMock.getArguments()[0]);

        //when
        fieldService.create(message, 4L);

        //then
        assertEquals(4, saved[0].getCol());
        assertEquals(4, saved[0].getRow());
        assertEquals(Mark.X, saved[0].getMark());
    }

    @Test
    public void shouldReturnListOfFieldDto(){
        //given
        Field field = Field.builder()
                .mark(Mark.X)
                .row(4)
                .col(4)
                .build();
        given(fieldDao.findByGameId(Mockito.any())).willReturn(Collections.singletonList(field));

        //when
        List<FieldDto> fields = fieldService.findByGameId(4L);

        //then
        assertEquals(4, fields.get(0).getCol());
        assertEquals(4, fields.get(0).getRow());
        assertEquals(Mark.X, fields.get(0).getMark());
    }

}
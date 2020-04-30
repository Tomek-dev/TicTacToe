package com.app.tictactoe.service;

import com.app.tictactoe.dao.FieldDao;
import com.app.tictactoe.dao.StopDao;
import com.app.tictactoe.model.Field;
import com.app.tictactoe.model.Game;
import com.app.tictactoe.model.Stop;
import com.app.tictactoe.other.builder.StopBuilder;
import com.app.tictactoe.other.enums.Mark;
import com.app.tictactoe.other.websocket.FieldAction;
import org.junit.Before;
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
    private StopDao stopDao;

    @InjectMocks
    private FieldService fieldService;

    private Stop stop;

    @Before
    public void init(){
        stop = StopBuilder.builder()
                .build();
    }

    @Test
    public void shouldSaveAll(){
        //given
        FieldAction fieldAction = new FieldAction();
        fieldAction.setCol(4);
        fieldAction.setRow(4);
        fieldAction.setMark(Mark.X);


        final List[] saved = new List[1];
        given(fieldDao.saveAll(Mockito.anyList())).willAnswer(invocationOnMock -> saved[0] = (List) invocationOnMock.getArguments()[0]);

        //when
        fieldService.saveAll(Collections.singleton(fieldAction), stop);

        //then
        List<Field> fields = saved[0];
        assertEquals(4, fields.get(0).getCol());
        assertEquals(4, fields.get(0).getRow());
        assertEquals(Mark.X, fields.get(0).getMark());


    }

    @Test
    public void shouldReturnSetOfFields(){
        //given
        Field field = Field.builder()
                .mark(Mark.X)
                .row(4)
                .col(4)
                .build();
        stop.setFields(Collections.singleton(field));
        given(stopDao.findByGameId(Mockito.any())).willReturn(Optional.of(stop));

        //when
        Set<FieldAction> fields = fieldService.findByGameId(4L);

        //then
        FieldAction[] array = new FieldAction[1];
        fields.toArray(array);
        assertEquals(4, array[0].getCol());
        assertEquals(4, array[0].getRow());
        assertEquals(Mark.X, array[0].getMark());
    }

}
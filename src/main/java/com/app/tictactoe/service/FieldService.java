package com.app.tictactoe.service;

import com.app.tictactoe.dao.FieldDao;
import com.app.tictactoe.dao.StopDao;
import com.app.tictactoe.model.Field;
import com.app.tictactoe.model.Stop;
import com.app.tictactoe.other.exceptions.StopNotFoundException;
import com.app.tictactoe.other.websocket.FieldAction;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FieldService {

    private final ModelMapper mapper = new ModelMapper();

    private FieldDao fieldDao;
    private StopDao stopDao;

    @Autowired
    public FieldService(FieldDao fieldDao, StopDao stopDao) {
        this.fieldDao = fieldDao;
        this.stopDao = stopDao;
    }

    public void saveAll(Set<FieldAction> messages, Stop stop){
        List<Field> fields =  messages.stream()
                .map(field -> Field.builder()
                        .col(field.getCol())
                        .row(field.getRow())
                        .mark(field.getMark())
                        .stop(stop)
                        .build()
                ).collect(Collectors.toList());
        fieldDao.saveAll(fields);
    }

    public Set<FieldAction> findByGameId(Long id){
        Optional<Stop> stopOptional = stopDao.findByGameId(id);
        Stop stop = stopOptional.orElseThrow(StopNotFoundException::new);
        return stop.getFields().stream()
                .map(field -> mapper.map(field, FieldAction.class))
                .collect(Collectors.toSet());
    }
}

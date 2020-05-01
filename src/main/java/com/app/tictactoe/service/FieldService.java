package com.app.tictactoe.service;

import com.app.tictactoe.dao.FieldDao;
import com.app.tictactoe.dao.GameDao;
import com.app.tictactoe.model.Field;
import com.app.tictactoe.model.Game;
import com.app.tictactoe.other.dto.FieldDto;
import com.app.tictactoe.other.exceptions.GameNotFoundException;
import com.app.tictactoe.other.websocket.GameMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FieldService {

    private final ModelMapper mapper = new ModelMapper();

    private FieldDao fieldDao;
    private GameDao gameDao;

    @Autowired
    public FieldService(FieldDao fieldDao, GameDao gameDao) {
        this.fieldDao = fieldDao;
        this.gameDao = gameDao;
    }

    public void create(GameMessage message, Long id){
        Optional<Game> gameOptional = gameDao.findById(id);
        Game game = gameOptional.orElseThrow(GameNotFoundException::new);
        Field field = Field.builder()
                .col(message.getCol())
                .row(message.getRow())
                .mark(message.getMark())
                .game(game)
                .build();
        fieldDao.save(field);
    }

    public List<FieldDto> findByGameId(Long id){
        List<Field> fields = fieldDao.findByGameId(id);
        return fields.stream()
                .map(field -> mapper.map(field, FieldDto.class))
                .collect(Collectors.toList());
    }
}

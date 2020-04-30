package com.app.tictactoe.service;

import com.app.tictactoe.dao.GameDao;
import com.app.tictactoe.dao.StopDao;
import com.app.tictactoe.model.Game;
import com.app.tictactoe.model.Stop;
import com.app.tictactoe.other.builder.StopBuilder;
import com.app.tictactoe.other.exceptions.GameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StopService {

    private StopDao stopDao;
    private GameDao gameDao;

    @Autowired
    public StopService(StopDao stopDao, GameDao gameDao) {
        this.stopDao = stopDao;
        this.gameDao = gameDao;
    }

    public Stop create(Long id){
        Optional<Game> gameOptional = gameDao.findById(id);
        Game game = gameOptional.orElseThrow(GameNotFoundException::new);
        Stop stop = StopBuilder.builder()
                .game(game)
                .build();
        return stopDao.save(stop);
    }

    public Boolean existsByGameId(Long id){
        return stopDao.existsByGameId(id);
    }

    public void deleteByGameId(Long id){
        stopDao.deleteByGameId(id);
    }
}

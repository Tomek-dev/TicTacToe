package com.app.tictactoe.service;

import com.app.tictactoe.dao.GameDao;
import com.app.tictactoe.dao.DisconnectDao;
import com.app.tictactoe.model.Game;
import com.app.tictactoe.model.Disconnect;
import com.app.tictactoe.other.builder.DisconnectBuilder;
import com.app.tictactoe.other.enums.Leave;
import com.app.tictactoe.other.exceptions.DisconnectNotFoundException;
import com.app.tictactoe.other.exceptions.GameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DisconnectService {

    private DisconnectDao disconnectDao;
    private GameDao gameDao;

    @Autowired
    public DisconnectService(DisconnectDao disconnectDao, GameDao gameDao) {
        this.disconnectDao = disconnectDao;
        this.gameDao = gameDao;
    }

    public void create(Long id, Leave leave){
        Optional<Game> gameOptional = gameDao.findById(id);
        Game game = gameOptional.orElseThrow(GameNotFoundException::new);
        if(game.getDisconnect() == null){
            Disconnect disconnect = DisconnectBuilder.builder()
                    .leave(leave)
                    .game(game)
                    .build();
            disconnectDao.save(disconnect);
            return;
        }
        if(!game.getDisconnect().getLeave().equals(leave)){
            game.getDisconnect().setLeave(Leave.BOTH_LEFT);
            disconnectDao.save(game.getDisconnect());
        }
    }

    public Boolean reconnect(Long id, Leave leave) {
        Optional<Disconnect> disconnectOptional = disconnectDao.findByGameId(id);
        Disconnect disconnect = disconnectOptional.orElseThrow(DisconnectNotFoundException::new);
        if(disconnect.getLeave().equals(Leave.BOTH_LEFT)){
            disconnect.setLeave(leave);
            disconnectDao.save(disconnect);
            return true;
        }
        disconnectDao.delete(disconnect);
        return false;
    }
}

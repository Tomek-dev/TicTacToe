package com.app.tictactoe.service;

import com.app.tictactoe.dao.PreGameDao;
import com.app.tictactoe.model.Player;
import com.app.tictactoe.model.PreGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PreGameService {

    private PreGameDao preGameDao;

    @Autowired
    public PreGameService(PreGameDao preGameDao) {
        this.preGameDao = preGameDao;
    }

    public Boolean exists(Player player){
        return preGameDao.existsByPlayerNot(player);
    }

    public void create(Player player){
        if(!preGameDao.existsByPlayer(player)){
            PreGame preGame = PreGame.builder()
                    .player(player)
                    .build();
            preGameDao.save(preGame);
        }
    }

    @Transactional
    public void delete(Player player){
        preGameDao.deleteByPlayer(player);
    }
}

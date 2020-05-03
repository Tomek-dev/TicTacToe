package com.app.tictactoe.config;

import com.app.tictactoe.dao.DisconnectDao;
import com.app.tictactoe.dao.GameDao;
import com.app.tictactoe.model.Disconnect;
import com.app.tictactoe.model.Game;
import com.app.tictactoe.other.enums.Leave;
import com.app.tictactoe.other.enums.Process;
import com.app.tictactoe.other.enums.Type;
import com.app.tictactoe.other.enums.Win;
import com.app.tictactoe.other.websocket.WinMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Configuration
@EnableScheduling
public class ScheduleConfig {

    private SimpMessageSendingOperations sendingOperations;
    private DisconnectDao disconnectDao;
    private GameDao gameDao;

    @Autowired
    public ScheduleConfig(SimpMessageSendingOperations sendingOperations,
                          DisconnectDao disconnectDao, GameDao gameDao) {
        this.sendingOperations = sendingOperations;
        this.disconnectDao = disconnectDao;
        this.gameDao = gameDao;
    }

    @Scheduled(fixedDelay = 30000)
    public void disconnectSchedule(){
        List<Disconnect> disconnects = disconnectDao.findByDateLessThanEqual(LocalDateTime.now().minusSeconds(120));
        List<Game> games = new LinkedList<>();
        disconnects.forEach(disconnect -> {
            Leave leave = disconnect.getLeave();
            Game game = disconnect.getGame();
            WinMessage message = new WinMessage();
            message.setType(Type.WIN);
            if(leave.equals(Leave.O_LEFT)){
                game.setWinner(Win.O_LEFT);
                message.setWinner(Win.O_LEFT);
                sendingOperations.convertAndSendToUser(game.getX().getUser().getUsername(), "/queue/game", message);
            }
            if(leave.equals(Leave.X_LEFT)){
                game.setWinner(Win.X_LEFT);
                message.setWinner(Win.X_LEFT);
                sendingOperations.convertAndSendToUser(game.getO().getUser().getUsername(), "/queue/game", message);
            }
            if(leave.equals(Leave.BOTH_LEFT)){
                game.setWinner(Win.DRAW);
            }
            game.setProcess(Process.ENDED);
            games.add(disconnect.getGame());
        });
        gameDao.saveAll(games);
        disconnectDao.deleteAll(disconnects);
    }
}
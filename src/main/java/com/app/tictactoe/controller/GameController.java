package com.app.tictactoe.controller;

import com.app.tictactoe.model.Game;
import com.app.tictactoe.model.Stop;
import com.app.tictactoe.model.User;
import com.app.tictactoe.other.dto.GameDto;
import com.app.tictactoe.other.enums.Type;
import com.app.tictactoe.other.websocket.*;
import com.app.tictactoe.service.FieldService;
import com.app.tictactoe.service.GameService;
import com.app.tictactoe.service.PreGameService;
import com.app.tictactoe.service.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
public class GameController {

    private SimpMessageSendingOperations sendingOperations;
    private StopService stopService;
    private GameService gameService;
    private FieldService fieldService;
    private PreGameService preGameService;

    @Autowired
    public GameController(SimpMessageSendingOperations sendingOperations, StopService stopService,
                          GameService gameService, FieldService fieldService, PreGameService preGameService) {
        this.sendingOperations = sendingOperations;
        this.stopService = stopService;
        this.gameService = gameService;
        this.fieldService = fieldService;
        this.preGameService = preGameService;
    }

    //connect
    //checks if exists a game that is in during with specific user
    //if yes frontend will reconnect to the game
    //if not exists frontend will create/join to game
    @MessageMapping("/game.connect")
    public void connect(@AuthenticationPrincipal User user){
        if(gameService.existsGame(user.getPlayer())){
            sendingOperations.convertAndSendToUser(user.getUsername(), "/queue/game", new InfoMessage(Type.RECONNECT, "Reconnected to the game."));
        }
    }

    //create
    //if exists any pre-game then creates a new game, else creates new pre-game
    @MessageMapping("/game.create")
    public void create(@AuthenticationPrincipal User user){
        if (preGameService.existsAny()){
            Game game = gameService.create(user.getPlayer());
            InfoMessage message = new InfoMessage(Type.CREATE, game.getId().toString());
            sendingOperations.convertAndSendToUser(game.getX().getUser().getUsername(), "/queue/game", message);
            sendingOperations.convertAndSendToUser(game.getO().getUser().getUsername(), "/queue/game", message);
            return;
        }
        preGameService.create(user.getPlayer());
    }

    //send
    //sends message informing about the opponent's action
    @MessageMapping("/game/{username}.send")
    public void send(@Payload GameMessage gameMessage,
                     @DestinationVariable String username,
                     @AuthenticationPrincipal User user){
        sendingOperations.convertAndSendToUser(username, "/queue/game", gameMessage);
        sendingOperations.convertAndSendToUser(user.getUsername(), "/queue", gameMessage);
    }

    //delete
    //removes pre-game if user disconnects while searching opponent
    @MessageMapping("/game.delete")
    public void delete(@AuthenticationPrincipal User user){
        preGameService.delete(user.getPlayer());
    }

    //stop
    //saves the actual state of the game to DB and inform opponent about a disconnect
    //if both of players disconnect removes the game
    @MessageMapping("/game/{username}/{id}.stop")
    public void stop(@Payload StopMessage stopMessage, @DestinationVariable String username, @DestinationVariable Long id){
        if(!stopService.existsByGameId(id)){
            Stop stop = stopService.create(id);
            fieldService.saveAll(stopMessage.getFields(), stop);
            InfoMessage message = new InfoMessage(Type.STOP, "The opponent has left the game.");
            sendingOperations.convertAndSendToUser(username, "/queue/game", message);
        }
    }

    //update
    //updates state of the game after disconnect
    @MessageMapping("/game/{id].update")
    public void update(@DestinationVariable Long id){
        Set<FieldAction> fields = fieldService.findByGameId(id);
        UpdateMessage message = new UpdateMessage(Type.UPDATE, fields);
        GameDto gameDto = gameService.findById(id);
        sendingOperations.convertAndSendToUser(gameDto.getO(), "/queue/game", message);
        sendingOperations.convertAndSendToUser(gameDto.getX(), "/queue/game", message);
        stopService.deleteByGameId(id);
    }

    //win
    //sets winner of the game
    @MessageMapping("/game/{username}/{id}.win")
    public void win(@Payload WinMessage message, @DestinationVariable Long id, @DestinationVariable String username){
        gameService.setWinner(id, message.getWinner());
        stopService.deleteByGameId(id);
        sendingOperations.convertAndSendToUser(username, "/queue/game", message);
    }
}

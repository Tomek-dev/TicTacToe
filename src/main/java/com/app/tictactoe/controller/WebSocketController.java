package com.app.tictactoe.controller;

import com.app.tictactoe.model.Game;
import com.app.tictactoe.model.User;
import com.app.tictactoe.other.dto.FieldDto;
import com.app.tictactoe.other.dto.GameDto;
import com.app.tictactoe.other.enums.Leave;
import com.app.tictactoe.other.enums.Type;
import com.app.tictactoe.other.websocket.*;
import com.app.tictactoe.service.FieldService;
import com.app.tictactoe.service.GameService;
import com.app.tictactoe.service.PreGameService;
import com.app.tictactoe.service.DisconnectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WebSocketController {

    private SimpMessageSendingOperations sendingOperations;
    private DisconnectService disconnectService;
    private GameService gameService;
    private FieldService fieldService;
    private PreGameService preGameService;

    @Autowired
    public WebSocketController(SimpMessageSendingOperations sendingOperations, DisconnectService disconnectService,
                               GameService gameService, FieldService fieldService, PreGameService preGameService) {
        this.sendingOperations = sendingOperations;
        this.disconnectService = disconnectService;
        this.gameService = gameService;
        this.fieldService = fieldService;
        this.preGameService = preGameService;
    }

    //connect
    //checks if exists a game that is in during with specific user
    //if yes frontend will reconnect to the game
    //if not exists frontend will create/join to game
    @MessageMapping("/game.connect")
    public void connect(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        if(gameService.existActualGame(user.getPlayer())){
            sendingOperations.convertAndSendToUser(user.getUsername(), "/queue/game", new InfoMessage(Type.RECONNECT, "Reconnected to the game."));
            return;
        }
        if (preGameService.exists(user.getPlayer())){
            Game game = gameService.create(user.getPlayer());
            CreateMessage message = new CreateMessage();
            message.setType(Type.CREATE);
            message.setId(game.getId());
            message.setO(game.getO().getUser().getUsername());
            message.setX(game.getX().getUser().getUsername());
            sendingOperations.convertAndSendToUser(game.getX().getUser().getUsername(), "/queue/game", message);
            sendingOperations.convertAndSendToUser(game.getO().getUser().getUsername(), "/queue/game", message);
            return;
        }
        preGameService.create(user.getPlayer());
    }

    //send
    //sends message informing about the opponent's action and save it to DB
    @MessageMapping("/game/{id}.send")
    public void send(@Payload GameMessage message,
                     @DestinationVariable Long id){
        GameDto gameDto = gameService.findById(id);
        fieldService.create(message, id);
        sendingOperations.convertAndSendToUser(gameDto.getOUserUsername(), "/queue/game", message);
        sendingOperations.convertAndSendToUser(gameDto.getXUserUsername(), "/queue/game", message);
    }

    //reconnect
    //updates state of the game after disconnect
    //If one of the players disconnected then delete disconnect from DB
    //else if both players disconnected set enum leave
    //and send info to the connected player that opponent left the game
    @MessageMapping("/game.reconnect")
    public void reconnect(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        GameDto gameDto = gameService.findActualGame(user.getPlayer());
        List<FieldDto> fields = fieldService.findByGameId(gameDto.getId());
        ReconnectMessage message = new ReconnectMessage();
        message.setType(Type.STATE);
        message.setId(gameDto.getId());
        message.setO(gameDto.getOUserUsername());
        message.setX(gameDto.getXUserUsername());
        message.setFields(fields);
        sendingOperations.convertAndSendToUser(user.getUsername(), "/queue/game", message);
        if(disconnectService.reconnect(gameDto.getId(), (user.getUsername().equals(gameDto.getOUserUsername())? Leave.O_LEFT : Leave.X_LEFT))){
            sendingOperations.convertAndSendToUser(user.getUsername(), "/queue/game", new InfoMessage(Type.DISCONNECT, "The opponent has left the game."));
        }
    }

    //win
    //sets winner of the game
    @MessageMapping("/game/{id}.win")
    public void win(@Payload WinMessage message, @DestinationVariable Long id){
        gameService.setWinner(id, message.getWinner());
        GameDto gameDto = gameService.findById(id);
        sendingOperations.convertAndSendToUser(gameDto.getOUserUsername(), "/queue/game", message);
        sendingOperations.convertAndSendToUser(gameDto.getXUserUsername(), "/queue/game", message);
    }
}

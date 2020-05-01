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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GameController {

    private SimpMessageSendingOperations sendingOperations;
    private DisconnectService disconnectService;
    private GameService gameService;
    private FieldService fieldService;
    private PreGameService preGameService;

    @Autowired
    public GameController(SimpMessageSendingOperations sendingOperations, DisconnectService disconnectService,
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
    public void connect(@AuthenticationPrincipal User user){
        if(gameService.existActualGame(user.getPlayer())){
            sendingOperations.convertAndSendToUser(user.getUsername(), "/queue/game", new InfoMessage(Type.RECONNECT, "Reconnected to the game."));
            return;
        }
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
    //sends message informing about the opponent's action and save it to DB
    @MessageMapping("/game/{id}.send")
    public void send(@Payload GameMessage message,
                     @DestinationVariable Long id){
        GameDto gameDto = gameService.findById(id);
        fieldService.create(message, id);
        sendingOperations.convertAndSendToUser(gameDto.getOUserUsername(), "/queue/game", message);
        sendingOperations.convertAndSendToUser(gameDto.getXUserUsername(), "/queue/game", message);
    }

    //delete
    //removes pre-game if user disconnects while searching opponent
    @MessageMapping("/game.delete")
    public void delete(@AuthenticationPrincipal User user){
        preGameService.delete(user.getPlayer());
    }

    //disconnect
    //create new disconnect
    @MessageMapping("/game/{id}.disconnect")
    public void disconnect(@Payload DisconnectMessage message, @DestinationVariable Long id, @AuthenticationPrincipal User user){
        disconnectService.create(id, message.getLeave());
        sendingOperations.convertAndSendToUser(message.getPlayer(), "/queue/game", new InfoMessage(Type.DISCONNECT, "The opponent has left the game."));
        sendingOperations.convertAndSendToUser(user.getUsername(), "/queue/game", new InfoMessage(Type.RECONNECT, "Reconnected to the game."));
    }

    //reconnect
    //updates state of the game after disconnect
    //If one of the players disconnected then delete disconnect from DB
    //else if both players disconnected set enum leave
    //and send info to the connected player that opponent left the game
    @MessageMapping("/game.reconnect")
    public void reconnect(@AuthenticationPrincipal User user){
        GameDto gameDto = gameService.findActualGame(user.getPlayer());
        List<FieldDto> fields = fieldService.findByGameId(gameDto.getId());
        ReconnectMessage message = new ReconnectMessage(Type.STATE, fields);
        sendingOperations.convertAndSendToUser(user.getUsername(), "/queue/game", message);
        if(disconnectService.reconnect(gameDto.getId(), (user.getUsername().equals(gameDto.getOUserUsername())? Leave.X_LEFT : Leave.O_LEFT))){
            sendingOperations.convertAndSendToUser(user.getUsername(), "/queue/game", new InfoMessage(Type.DISCONNECT, "The opponent has left the game."));
        }
    }

    //win
    //sets winner of the game
    @MessageMapping("/game/{username}/{id}.win")
    public void win(@Payload WinMessage message, @DestinationVariable Long id, @DestinationVariable String username){
        gameService.setWinner(id, message.getWinner());
        sendingOperations.convertAndSendToUser(username, "/queue/game", message);
    }
}

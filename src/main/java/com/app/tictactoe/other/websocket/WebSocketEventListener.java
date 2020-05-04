package com.app.tictactoe.other.websocket;

import com.app.tictactoe.dao.UserDao;
import com.app.tictactoe.model.User;
import com.app.tictactoe.other.dto.GameDto;
import com.app.tictactoe.other.enums.Leave;
import com.app.tictactoe.other.enums.Type;
import com.app.tictactoe.other.exceptions.UserNotFoundException;
import com.app.tictactoe.other.websocket.InfoMessage;
import com.app.tictactoe.security.UserPrincipal;
import com.app.tictactoe.service.DisconnectService;
import com.app.tictactoe.service.GameService;
import com.app.tictactoe.service.PreGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Optional;

@Component
public class WebSocketEventListener {

    private PreGameService preGameService;
    private DisconnectService disconnectService;
    private GameService gameService;
    private SimpMessageSendingOperations sendingOperations;

    @Autowired
    public WebSocketEventListener(PreGameService preGameService, DisconnectService disconnectService,
                                  GameService gameService, SimpMessageSendingOperations sendingOperations) {
        this.preGameService = preGameService;
        this.disconnectService = disconnectService;
        this.gameService = gameService;
        this.sendingOperations = sendingOperations;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        Authentication authentication = (Authentication) accessor.getUser();
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        if(user != null){
            if(gameService.existActualGame(user.getPlayer())){
                disconnectGame(user);
                return;
            }
            disconnectPreGame(user);
        }
    }

    private void disconnectGame(UserPrincipal user){
        GameDto gameDto = gameService.findActualGame(user.getPlayer());
        disconnectService.create(gameDto.getId(), (user.getUsername().equals(gameDto.getOUserUsername()) ? Leave.O_LEFT : Leave.X_LEFT));
        sendingOperations.convertAndSendToUser((user.getUsername().equals(gameDto.getOUserUsername()) ? gameDto.getXUserUsername() : gameDto.getOUserUsername()), "/queue/game", new InfoMessage(Type.DISCONNECT, "The opponent has left the game."));
        sendingOperations.convertAndSendToUser(user.getUsername(), "/queue/game", new InfoMessage(Type.RECONNECT, "Reconnected to the game."));
    }

    private void disconnectPreGame(UserPrincipal user){
        preGameService.delete(user.getPlayer());
    }
}

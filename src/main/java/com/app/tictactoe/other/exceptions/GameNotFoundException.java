package com.app.tictactoe.other.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such Game")
public class GameNotFoundException extends RuntimeException{
    public GameNotFoundException() {
        super("Game not found.");
    }

    public GameNotFoundException(String message) {
        super(message);
    }

    public GameNotFoundException(Throwable cause) {
        super(cause);
    }
}

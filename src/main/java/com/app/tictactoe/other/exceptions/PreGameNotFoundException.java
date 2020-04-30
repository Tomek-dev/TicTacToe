package com.app.tictactoe.other.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such PreGame")
public class PreGameNotFoundException extends RuntimeException{
    public PreGameNotFoundException() {
        super("PreGame not found.");
    }

    public PreGameNotFoundException(String message) {
        super(message);
    }

    public PreGameNotFoundException(Throwable cause) {
        super(cause);
    }
}

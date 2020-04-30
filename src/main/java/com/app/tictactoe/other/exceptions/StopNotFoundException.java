package com.app.tictactoe.other.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such Stop")
public class StopNotFoundException extends RuntimeException {
    public StopNotFoundException() {
        super("Stop not found.");
    }

    public StopNotFoundException(String message) {
        super(message);
    }

    public StopNotFoundException(Throwable cause) {
        super(cause);
    }
}

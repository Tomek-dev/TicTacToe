package com.app.tictactoe.other.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such Disconnect")
public class DisconnectNotFoundException extends RuntimeException {
    public DisconnectNotFoundException() {
        super("Stop not found.");
    }

    public DisconnectNotFoundException(String message) {
        super(message);
    }

    public DisconnectNotFoundException(Throwable cause) {
        super(cause);
    }
}

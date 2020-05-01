package com.app.tictactoe.other.websocket;

import com.app.tictactoe.other.enums.Leave;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DisconnectMessage {

    private Leave leave;
    private String player;
}

package com.app.tictactoe.other.websocket;

import com.app.tictactoe.other.enums.Mark;
import com.app.tictactoe.other.enums.Type;
import com.app.tictactoe.other.enums.Win;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WinMessage {

    private Type type;
    private Win winner;
    private String message;
}

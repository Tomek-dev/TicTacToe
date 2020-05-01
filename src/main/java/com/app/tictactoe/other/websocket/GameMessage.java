package com.app.tictactoe.other.websocket;

import com.app.tictactoe.other.enums.Mark;
import com.app.tictactoe.other.enums.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameMessage {

    private Type type;
    private Mark mark;
    private Integer row;
    private Integer col;
}

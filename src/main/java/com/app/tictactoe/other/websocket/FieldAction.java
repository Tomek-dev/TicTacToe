package com.app.tictactoe.other.websocket;

import com.app.tictactoe.other.enums.Mark;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldAction {

    private Mark mark;
    private Integer row;
    private Integer col;
}

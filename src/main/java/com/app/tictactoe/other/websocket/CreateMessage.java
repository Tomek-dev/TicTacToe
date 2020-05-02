package com.app.tictactoe.other.websocket;

import com.app.tictactoe.other.enums.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMessage {

    private Type type;
    private Long id;
    private String x;
    private String o;
}

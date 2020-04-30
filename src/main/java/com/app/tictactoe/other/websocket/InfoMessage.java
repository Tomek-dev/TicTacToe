package com.app.tictactoe.other.websocket;

import com.app.tictactoe.other.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfoMessage {

    private Type type;
    private String message;
}

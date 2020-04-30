package com.app.tictactoe.other.websocket;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class StopMessage {

    private String x;
    private String o;
    private Set<FieldAction> fields = new HashSet<>();
}

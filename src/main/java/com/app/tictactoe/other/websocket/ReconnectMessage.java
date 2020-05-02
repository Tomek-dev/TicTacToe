package com.app.tictactoe.other.websocket;

import com.app.tictactoe.other.dto.FieldDto;
import com.app.tictactoe.other.enums.Type;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReconnectMessage {

    private Type type;
    private Long id;
    private String x;
    private String o;
    private List<FieldDto> fields = new ArrayList<>();
}

package com.app.tictactoe.other.websocket;

import com.app.tictactoe.other.dto.FieldDto;
import com.app.tictactoe.other.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReconnectMessage {

    private Type type;
    private List<FieldDto> fields = new ArrayList<>();
}

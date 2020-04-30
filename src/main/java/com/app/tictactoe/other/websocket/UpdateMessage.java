package com.app.tictactoe.other.websocket;

import com.app.tictactoe.other.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMessage {

    private Type type;
    private Set<FieldAction> fields = new HashSet<>();
}

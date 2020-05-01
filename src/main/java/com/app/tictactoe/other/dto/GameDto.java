package com.app.tictactoe.other.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDto {

    private Long id;
    private String xUserUsername;
    private String oUserUsername;
}

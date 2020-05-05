package com.app.tictactoe.other.dto;

import com.app.tictactoe.other.enums.Win;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDto {

    private Long id;
    private String xUserUsername;
    private String oUserUsername;
}

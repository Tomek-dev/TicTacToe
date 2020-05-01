package com.app.tictactoe.other.dto;

import com.app.tictactoe.other.enums.Mark;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldDto {

    private Integer row;
    private Integer col;
    private Mark mark;
}

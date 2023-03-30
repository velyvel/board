package com.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class BoardAttachDto {

    private int attachNo;
    private String attachName;
    private String savedAttachName;
    //foreign key
    private int boardNo;

}

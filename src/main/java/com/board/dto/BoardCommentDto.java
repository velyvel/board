package com.board.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BoardCommentDto {

    private int commentNo;
    private String commentContent;
    //댓글 작성자, 닉네임
    private String commentWriter;
    private Date commentRegDate;
    //리스트라 데이터타입을 integer로 선언
    private Integer commentGroup;
    private Integer depth;
    private Integer step;
    //이건 foreign key, 보드 하나당 댓글 여러개, 리스트타입 아니므로 int로 선언
    private int boardNo;

}

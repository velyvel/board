package com.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class BoardDto {

    private int BoardNo;
    //나중에 로그인 기능 동작 시 loginId와 연계할 예정
    private String writer;
    private String boardTitle;
    private String boardContent;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date boardRegDate;

    private int boardCount;
    private Boolean boardDeleted;

    //one to many 위해 리스트 전체 받음
    private List<BoardAttachDto> boardAttachments;

    //one to many가 되는 이유: 파일명, 파일 경로 받아야 함, 그래서 리스트로 받음
    private List<BoardCommentDto> boardComments;


}

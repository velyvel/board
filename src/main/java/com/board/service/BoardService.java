package com.board.service;

import com.board.dto.BoardAttachDto;
import com.board.dto.BoardCommentDto;
import com.board.dto.BoardDto;
import com.board.entity.BoardAttachEntity;
import com.board.entity.BoardCommentEntity;
import com.board.entity.BoardEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface BoardService {

//board
    public default BoardDto boardEntityToDto(BoardEntity boardEntity){
        BoardDto boardDto = new BoardDto();
        boardDto.setBoardNo(boardEntity.getBoardNo());
        boardDto.setWriter(boardEntity.getWriter());
        boardDto.setBoardTitle(boardEntity.getBoardTitle());
        boardDto.setBoardContent(boardEntity.getBoardContent());
        boardDto.setBoardCount(boardEntity.getBoardCount());
        boardDto.setBoardDeleted(boardEntity.getBoardDeleted());
        boardDto.setBoardRegDate(boardEntity.getBoardRegDate());

        List<BoardAttachDto> boardAttachDtos = new ArrayList<>();
        for (BoardAttachEntity entity: boardEntity.getBoardAttachments()) {
            boardAttachDtos.add(boardAttachEntityToDto(entity));
        }
        boardDto.setBoardAttachments(boardAttachDtos);


        return boardDto;
    }

    public default BoardEntity boardDtoToEntity(BoardDto board){
        BoardEntity boardEntity = BoardEntity.builder()
                .boardNo(board.getBoardNo())
                .writer(board.getWriter())
                .boardTitle(board.getBoardTitle())
                .boardContent(board.getBoardContent())
                .boardCount(board.getBoardCount())
                .boardDeleted(board.getBoardDeleted())
                .build();
        return boardEntity;
    }
//attach
    public default BoardAttachDto boardAttachEntityToDto(BoardAttachEntity boardAttachEntity){
        BoardAttachDto boardAttachDto = new BoardAttachDto();
        boardAttachDto.setAttachName(boardAttachEntity.getAttachName());
        boardAttachDto.setSavedAttachName(boardAttachEntity.getSavedAttachName());
        boardAttachDto.setAttachNo(boardAttachEntity.getAttachNo());

        return boardAttachDto;
    }
    public default BoardAttachEntity boardAttachDtoToEntity(BoardAttachDto boardAttach){
        BoardAttachEntity boardAttachEntity = BoardAttachEntity.builder()
                .attachNo(boardAttach.getAttachNo())
                .attachName(boardAttach.getAttachName())
                .savedAttachName(boardAttach.getSavedAttachName())
                .build();
        return boardAttachEntity;
    }

    public default BoardCommentDto boardCommentEntityToDto(BoardCommentEntity commentEntity){
        BoardCommentDto commentDto = new BoardCommentDto();
        commentDto.setCommentNo(commentEntity.getCommentNo());
        commentDto.setCommentContent(commentEntity.getCommentContent());
        commentDto.setCommentRegDate(commentEntity.getCommentRegDate());
        commentDto.setCommentGroup(commentEntity.getCommentGroup());
        commentDto.setCommentWriter(commentEntity.getCommentWriter());
        commentDto.setDepth(commentEntity.getDepth());
        commentDto.setStep(commentEntity.getStep());

        return commentDto;
    }

    public default BoardCommentEntity boardCommentDtoToEntity(BoardCommentDto commentDto){
        BoardCommentEntity commentEntity = BoardCommentEntity.builder()
                .commentNo(commentDto.getCommentNo())
                .commentWriter(commentDto.getCommentWriter())
                .commentGroup(commentDto.getCommentGroup())
                .depth(commentDto.getDepth())
                .step(commentDto.getStep())
                .build();

        return commentEntity;
    }

    void writeBoard(BoardDto board);

    List<BoardDto> findAllBoard();
    HashMap<String, Object> findBoardByPage(int pageNo, int pageSize);

    BoardDto findBoardByBoardNo(int boardNo);

    void modifyBoard(BoardDto board);

    void deleteBoard(int boardNo);

    void increaseBoardReadCount(int boardNo);

    BoardAttachDto findBoardAttachByAttachNo(int attachNo);

    void writeComment(BoardCommentDto comment);

    List<BoardCommentDto> findComments(int boardNo);

    void deleteComment(int commentNo);

    void writeBoard2(BoardDto board);


    //void deleteBoard(BoardDto board);


}

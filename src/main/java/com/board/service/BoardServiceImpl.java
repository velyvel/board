package com.board.service;

import com.board.dto.BoardAttachDto;
import com.board.dto.BoardCommentDto;
import com.board.dto.BoardDto;
import com.board.entity.BoardAttachEntity;
import com.board.entity.BoardCommentEntity;
import com.board.entity.BoardEntity;
import com.board.repository.BoardAttachRepository;
import com.board.repository.BoardCommentRepository;
import com.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Service("boardService")
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardCommentRepository boardCommentRepository;
 @Autowired
    private BoardAttachRepository boardAttachRepository;


    //글쓰기(create, insert)
    @Override
    public void writeBoard(BoardDto board) {
        BoardEntity boardEntity = BoardEntity.builder()
                .boardTitle(board.getBoardTitle())
                .writer(board.getWriter())
                .boardContent(board.getBoardContent())
                .build();
        // 여기에 첨부파일 작업 추가해야함

        HashSet<BoardAttachEntity> boardAttachments = new HashSet<>();
        for(BoardAttachDto boardAttachDto : board.getBoardAttachments()){
            boardAttachments.add(boardAttachDtoToEntity(boardAttachDto));
        }
        boardEntity.setBoardAttachments(boardAttachments);

        boardRepository.save(boardEntity);

    }


    //리스트 보기(list read), 페이징 없는 로직
    @Override
    public List<BoardDto> findAllBoard() {
        List<BoardEntity> boardList = boardRepository.findAllByOrderByBoardNoDesc();
        ArrayList<BoardDto> boards = new ArrayList<>();
        for (BoardEntity boardEntity : boardList) {
            boards.add(boardEntityToDto(boardEntity));
        }
        return boards;
    }

    //리스트보기(list, read) 페이징 있는 로직
    @Override
    public HashMap<String, Object> findBoardByPage(int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("boardNo").descending());
        Page<BoardEntity> page = boardRepository.findAll(pageRequest);

        HashMap<String, Object> pagingData = new HashMap<>();
        pagingData.put("dataCount", (int)page.getTotalElements());
        pagingData.put("pageCount", page.getTotalPages());
        List<BoardEntity> boardList = page.getContent();

        ArrayList<BoardDto> boards = new ArrayList<>();
        for (BoardEntity boardEntity : boardList) {
            boards.add(boardEntityToDto(boardEntity));
        }

        pagingData.put("boards", boards);

        return pagingData;
    }


    //글 상세보기(detail read)
    @Override
    public BoardDto findBoardByBoardNo(int boardNo) {

        BoardEntity boardEntity = boardRepository.findByBoardNo(boardNo);
        BoardDto board = boardEntityToDto(boardEntity);
        return board;
    }

    //글 수정하기(update)
    @Override
    public void modifyBoard(BoardDto board) {

        BoardEntity boardEntity = boardRepository.findByBoardNo(board.getBoardNo());
        boardEntity.setBoardTitle(board.getBoardTitle());
        boardEntity.setBoardContent(board.getBoardContent());
        boardEntity.setWriter(board.getWriter());
        //boardEntity.setBoardRegDate(board.getBoardRegDate());

        boardRepository.save(boardEntity);
        System.out.println(boardEntity);
    }

    //글 삭제하기(Delete)
    @Override
    public void deleteBoard(int boardNo) {
        BoardEntity boardEntity = boardRepository.findByBoardNo(boardNo);
        boardRepository.delete(boardEntity);
    }
    //////////optional//////////
    //조회수 증가
    @Override
    public void increaseBoardReadCount(int boardNo) {
        boardRepository.increaseBoardCount(boardNo);
    }

    //첨부파일 번호로 이름찾고 다운로드하고 함
    @Override
    public BoardAttachDto findBoardAttachByAttachNo(int attachNo) {
        BoardAttachEntity boardAttachEntity = boardAttachRepository.findBoardAttachByAttachNo(attachNo);

        return boardAttachEntityToDto(boardAttachEntity);
    }

    @Override
    public void writeComment(BoardCommentDto comment) {
        BoardEntity board = boardRepository.findByBoardNo(comment.getBoardNo());
        BoardCommentEntity commentEntity = BoardCommentEntity.builder()
                .commentNo(comment.getCommentNo())
                .commentContent(comment.getCommentContent())
                .commentWriter(comment.getCommentWriter())
                .commentGroup(comment.getCommentGroup())
                .board(board)
                .build();

        boardCommentRepository.save(commentEntity);
    }

    @Override
    public List<BoardCommentDto> findComments(int boardNo) {
        List<BoardCommentEntity> commentList = boardCommentRepository.findBoardCommentDesc(boardNo);
        ArrayList<BoardCommentDto> comments = new ArrayList<>();
        for(BoardCommentEntity commentEntity : commentList){
            comments.add(boardCommentEntityToDto(commentEntity));
        }
        return comments;
    }

    @Override
    public void deleteComment(int commentNo) {

        BoardCommentEntity boardCommentEntity = boardCommentRepository.findByBoardCommentNo(commentNo);
        boardCommentRepository.delete(boardCommentEntity);

    }

    @Override
    public void writeBoard2(BoardDto board) {
        BoardEntity boardEntity = BoardEntity.builder()
                .boardTitle(board.getBoardTitle())
                .writer(board.getWriter())
                .boardContent(board.getBoardContent())
                .build();
        boardRepository.save(boardEntity);

    }

//    @Override
//    public void deleteBoard(BoardDto board) {
//        BoardEntity boardEntity = boardRepository.findByBoardNo(board.getBoardNo());
//        boardEntity.setBoardTitle(board.getBoardTitle());
//        boardEntity.setBoardContent(board.getBoardContent());
//        boardEntity.setWriter(board.getWriter());
//        boardEntity.setBoardRegDate(board.getBoardRegDate());
//        boardEntity.setBoardDeleted(true);
//        boardRepository.save(boardEntity);
//    }


}

package com.board.repository;

import com.board.dto.BoardCommentDto;
import com.board.entity.BoardCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardCommentRepository extends JpaRepository<BoardCommentEntity, Integer> {

    @Query(value = "select * from tbl_comment WHERE boardNo= :boardNo", nativeQuery = true)
    List<BoardCommentEntity> findBoardCommentDesc(@Param("boardNo") int boardNo);

    @Query(value = "select * from tbl_commennt where commentNNo= commentNo", nativeQuery=true)
    BoardCommentEntity findByBoardCommentNo(@Param("commentNo") int commentNo);
}

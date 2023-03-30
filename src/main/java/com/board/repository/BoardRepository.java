package com.board.repository;

import com.board.entity.BoardAttachEntity;
import com.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    @Query(value = "SELECT * FROM tbl_board WHERE boardDeleted = 0 ORDER BY boardRegDate DESC", nativeQuery = true)
    List<BoardEntity> findAllByOrderByBoardNoDesc();

    BoardEntity findByBoardNo(int boardNo);


    @Transactional
    @Modifying
    @Query(value = "UPDATE tbl_board b set b. boardCount = b.boardCount + 1 WHERE b.boardNo = :boardNo", nativeQuery = true)
    void increaseBoardCount(@Param("boardNo") int boardNo);

}

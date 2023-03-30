package com.board.repository;

import com.board.entity.BoardAttachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BoardAttachRepository extends JpaRepository<BoardAttachEntity, Integer> {

    @Query(value="SELECT * FROM tbl_boardAttach WHERE attachNo = :attachNo", nativeQuery = true)
    BoardAttachEntity findBoardAttachByAttachNo(@Param("attachNo") int attachNo);

}

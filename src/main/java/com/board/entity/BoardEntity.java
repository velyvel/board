package com.board.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_board")
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private int boardNo;

    @Column(nullable = false)
    private String writer;

    @Column(length = 100, nullable = false)
    private String boardTitle;

    @Column(length = 1000, nullable = false)
    private String boardContent;

    @Builder.Default
    @Column(nullable = false)
    private Date boardRegDate = new Date();

    @Builder.Default
    @Column(nullable = false)
    private int boardCount = 0;

    @Builder.Default
    @Column(nullable = false)
    private Boolean boardDeleted = false;

    //첨부파일 테이블
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "boardNo")
    private Collection<BoardAttachEntity> boardAttachments;

    //one to many가 되는 이유: 파일명, 파일 경로 받아야 함, 그래서 리스트로 받음
    //반대로 boardCommentEntity는 many to oneㅇ으로 묶여야 함
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Collection<BoardCommentEntity> boardComments;

}

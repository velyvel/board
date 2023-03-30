package com.board.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_comment")
public class BoardCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private int commentNo;

    @Column
    private String commentContent;

    //댓글 작성자, 닉네임; 따라서 board의 writer랑은 다름, 추후 로그인아이디가 될 예정
    @Column
    private String commentWriter;

    @Builder.Default
    @Column
    private Date commentRegDate = new Date();

    //리스트라 데이터타입을 integer로 선언
    @Column
    private Integer commentGroup;

    @Column
    private Integer depth;

    @Column
    private Integer step;

    //이건 foreign key, 보드 하나당 댓글 여러개, 리스트타입 아니므로 int로 선언
    @ManyToOne
    @JoinColumn(name = "boardNo")
    private BoardEntity board;

}

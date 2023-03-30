package com.board.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_boardAttach")

public class BoardAttachEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int attachNo;

    //사용자가 올린 이름
    @Column
    private String attachName;

    //서버에 저장되는 이름
    @Column
    private String savedAttachName;


}

package com.zhxy.photostudio.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_notice")
@Data
// https://colorlib.com/polygon/gentelella/form_wizards.html
// https://pratikborsadiya.in
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;

    private String content;

    private Long updateTime;
}

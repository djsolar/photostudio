package com.zhxy.photostudio.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_commodity")
@Data
public class Commodity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 64)
    private String name;

    @Column(length = 64)
    private String type;

    @Column(length = 64)
    private String price;

    @Column(length = 8)
    private String unit;

    private Long createTime;

    private Long updateTime;
}

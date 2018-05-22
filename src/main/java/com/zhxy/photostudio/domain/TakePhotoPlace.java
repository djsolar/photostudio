package com.zhxy.photostudio.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_place")
@Data
public class TakePhotoPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 256)
    private String name;

    @Column(length = 1024)
    private String description;
}

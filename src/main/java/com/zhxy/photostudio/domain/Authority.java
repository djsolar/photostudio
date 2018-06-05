package com.zhxy.photostudio.domain;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "t_authority")
@Data
public class Authority{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private Integer code;

}

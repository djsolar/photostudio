package com.zhxy.photostudio.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_customer")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 64)
    private String manName;

    @Column(length = 64)
    private String womenName;

    @Column(length = 11)
    private String phone;

    @Column(length = 256)
    private String address;

    @Column(length = 256)
    private String weedingDate;

    private Long updateTime;

    private Long createTime;

    @OneToOne(mappedBy = "customer")
    private Order order;
}

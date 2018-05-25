package com.zhxy.photostudio.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_order")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // 拍摄时间
    private Long takePhotoTime;

    // 选片时间
    private Long selectPhotoTime;

    // 取片时间
    private Long pickPhotoTime;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServicePackage servicePackage;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // 订单时间
    private Long createTime;

    private Long updateTime;

    // 备注
    private String remark;

}

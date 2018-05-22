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

    // 订单时间
    private Long orderTime;

    // 拍摄时间
    private Long takePhotoTime;

    // 选片时间
    private Long selectPhotoTime;

    // 取片时间
    private Long pickPhotoTime;

    // 备注
    private String remark;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServicePackage servicePackage;

}

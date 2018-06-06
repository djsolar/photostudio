package com.zhxy.photostudio.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "t_order")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // 拍摄时间
    private String takePhotoTime;

    // 选片时间
    private String selectPhotoTime;

    // 取片时间
    private String pickPhotoTime;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServicePackage servicePackage;

    @JsonBackReference
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private Integer price;

    private Integer earnest;

    private Boolean deleted;

    // 订单时间
    private Long createTime;

    private Long updateTime;

    // 备注
    private String remark;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return Objects.equals(getId(), order.getId()) &&
                Objects.equals(getTakePhotoTime(), order.getTakePhotoTime()) &&
                Objects.equals(getSelectPhotoTime(), order.getSelectPhotoTime()) &&
                Objects.equals(getPickPhotoTime(), order.getPickPhotoTime()) &&
                Objects.equals(getCustomer(), order.getCustomer()) &&
                Objects.equals(getPrice(), order.getPrice()) &&
                Objects.equals(getEarnest(), order.getEarnest()) &&
                Objects.equals(getCreateTime(), order.getCreateTime()) &&
                Objects.equals(getUpdateTime(), order.getUpdateTime()) &&
                Objects.equals(getRemark(), order.getRemark());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getId(), getTakePhotoTime(), getSelectPhotoTime(), getPickPhotoTime(), getCustomer(), getPrice(), getEarnest(), getCreateTime(), getUpdateTime(), getRemark());
    }
}

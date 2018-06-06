package com.zhxy.photostudio.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

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

    private Boolean deleted;

    private Long updateTime;

    private Long createTime;

    @OneToOne(mappedBy = "customer")
    private Order order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        if (!super.equals(o)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getId(), customer.getId()) &&
                Objects.equals(getManName(), customer.getManName()) &&
                Objects.equals(getWomenName(), customer.getWomenName()) &&
                Objects.equals(getPhone(), customer.getPhone()) &&
                Objects.equals(getAddress(), customer.getAddress()) &&
                Objects.equals(getWeedingDate(), customer.getWeedingDate()) &&
                Objects.equals(getUpdateTime(), customer.getUpdateTime()) &&
                Objects.equals(getCreateTime(), customer.getCreateTime());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getId(), getManName(), getWomenName(), getPhone(), getAddress(), getWeedingDate(), getUpdateTime(), getCreateTime());
    }
}

package com.zhxy.photostudio.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "t_service_package")
@Data
public class ServicePackage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // 套系名称
    private String name;

    // 可选衣服套数
    private Integer clothesNumber;

    // 拍摄照片数
    private Integer photoNumber;

    // 可选照片数
    private Integer pickPhotoNumber;

    // 套餐价格
    private Integer price;

    @OneToMany(mappedBy = "servicePackage", fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<Order> orders;

    private Boolean deleted;

    // 备注
    @Column(length = 1024)
    private String remark;

    private Long updateTime;

    private Long createTime;

    // 可拍摄的地点
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "service_place", joinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "place_id", referencedColumnName = "id"))
    private List<TakePhotoPlace> takePhotoPlaces;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServicePackage)) return false;
        if (!super.equals(o)) return false;
        ServicePackage that = (ServicePackage) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getClothesNumber(), that.getClothesNumber()) &&
                Objects.equals(getPhotoNumber(), that.getPhotoNumber()) &&
                Objects.equals(getPickPhotoNumber(), that.getPickPhotoNumber()) &&
                Objects.equals(getPrice(), that.getPrice()) &&
                Objects.equals(getRemark(), that.getRemark()) &&
                Objects.equals(getUpdateTime(), that.getUpdateTime()) &&
                Objects.equals(getCreateTime(), that.getCreateTime());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getId(), getName(), getClothesNumber(), getPhotoNumber(), getPickPhotoNumber(), getPrice(), getRemark(), getUpdateTime(), getCreateTime());
    }
}

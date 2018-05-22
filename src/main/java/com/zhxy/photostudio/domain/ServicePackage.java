package com.zhxy.photostudio.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

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

    private String imageInfo;

    // 套餐价格
    private Integer price;

    // 备注
    @Column(length = 256)
    private String remark;

    // 可拍摄的地点
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "service_place", joinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "place_id", referencedColumnName = "id"))
    private List<TakePhotoPlace> takePhotoPlaces;

    // 套餐包含的商品
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "service_place", joinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "commodity_id", referencedColumnName = "id"))
    private List<Commodity> commodities;
}

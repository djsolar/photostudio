package com.zhxy.photostudio.util;

import lombok.Data;

@Data
public class OrderView {
    private Integer id;
    private String manName;
    private String womenName;
    private String address;
    private Integer customerId;
    private Integer serviceId;
    private String takePhotoTime;
    private String selectPhotoTime;
    private String pickPhotoTime;
    private String remark;
    private Integer price;
    private Integer earnest;
    private String phone;
    private String serviceName;
    private Long updateTime;
}

package com.zhxy.photostudio.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_activity")
@Data
// https://colorlib.com/polygon/gentelella/form_wizards.html
// https://pratikborsadiya.in
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String caption;

    private String description;

    private String startDate;

    private String endDate;

    private Boolean deleted;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "thumbnail_photo")
    private Photo thumbnailPhoto;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "detail_photo")
    private Photo detailPhoto;

    private Long updateTime;

    private Long createTime;
}

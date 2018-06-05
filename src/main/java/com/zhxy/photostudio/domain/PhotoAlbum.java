package com.zhxy.photostudio.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Action 相册
 *
 */
@Entity
@Table(name = "t_photo_album")
@Data
public class PhotoAlbum {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String caption;

    private String description;

    // 类型
    private String category;

    // 是否置顶
    private Boolean top;

    // 相册合集
    @ManyToOne
    @JoinColumn(name = "thumbnail_id")
    private Photo thumbnailPhoto;

    @ManyToOne
    @JoinColumn(name = "content_photo_id")
    private Photo contentPhoto;

    // 更新时间
    private Long updateTime;

    private Long createTime;
}

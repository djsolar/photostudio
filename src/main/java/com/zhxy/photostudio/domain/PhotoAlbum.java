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

    // 类型
    private String category;

    // 是否置顶
    private Boolean top;

    // 相册合集
    @ManyToMany
    @JoinTable(name = "album_photo", joinColumns ={@JoinColumn(name = "album_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "photo_id", referencedColumnName = "id")})
    private List<Photo> photos;

    // 更新时间
    private Long updateTime;
}

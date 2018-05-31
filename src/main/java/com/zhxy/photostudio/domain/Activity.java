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

    private String title;

    @ManyToOne
    @JoinColumn(name = "thumbnail_photo")
    private Photo thumbnail;

    @ManyToMany
    @JoinTable(joinColumns = {@JoinColumn(name = "activity_id", referencedColumnName="id")},
    inverseJoinColumns = {@JoinColumn(name = "photo_id", referencedColumnName = "id")})
    private List<Photo> photos;

    private Long updateTime;
}

package com.zhxy.photostudio.repository;

import com.zhxy.photostudio.domain.PhotoAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhotoAlbumRepository extends JpaRepository<PhotoAlbum, Integer> {


    @Modifying
    @Query("update PhotoAlbum set top = :top where id = :id")
    int updateTop(@Param("id") Integer id, @Param("top") Boolean top);

    @Modifying
    @Query("update PhotoAlbum set deleted = true where id = :id")
    int deletePhotoAlbum(@Param("id") Integer id);
}

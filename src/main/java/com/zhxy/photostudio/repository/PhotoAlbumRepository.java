package com.zhxy.photostudio.repository;

import com.zhxy.photostudio.domain.PhotoAlbum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoAlbumRepository extends JpaRepository<PhotoAlbum, Integer> {
}

package com.zhxy.photostudio.repository;

import com.zhxy.photostudio.domain.Photo;
import org.springframework.data.repository.CrudRepository;

public interface PhotoRepository extends CrudRepository<Photo, Integer> {
}

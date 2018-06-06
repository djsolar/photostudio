package com.zhxy.photostudio.repository;

import com.zhxy.photostudio.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer>, JpaSpecificationExecutor<Activity> {

    @Modifying
    @Query(value="update Activity set deleted = true where id = :id")
    int deleteActivity(@Param("id") Integer id);
}

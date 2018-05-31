package com.zhxy.photostudio.repository;

import com.zhxy.photostudio.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Activity, Integer> {

}

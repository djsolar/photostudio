package com.zhxy.photostudio.service;

import com.zhxy.photostudio.domain.Activity;
import com.zhxy.photostudio.util.ActivityView;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ActivityService {

    void delete(Integer id);

    Activity save(Integer id, String caption, String description, String startDate, String endDate, MultipartFile activityThumbnail, MultipartFile activityPhoto);

    List<Activity> listActivity();

    List<Activity> listValidActivity();

    ActivityView findActivityContent(Integer id);

    Activity findActivityById(Integer id);
}

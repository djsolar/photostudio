package com.zhxy.photostudio.service.impl;

import com.zhxy.photostudio.domain.Activity;
import com.zhxy.photostudio.domain.Photo;
import com.zhxy.photostudio.repository.ActivityRepository;
import com.zhxy.photostudio.repository.PhotoRepository;
import com.zhxy.photostudio.service.ActivityService;
import com.zhxy.photostudio.util.ActivityView;
import com.zhxy.photostudio.util.Config;
import com.zhxy.photostudio.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private Config config;

    @Override
    public void delete(Integer id) {
        activityRepository.deleteById(id);
    }

    @Override
    public Activity save(Integer id, String caption, String description, String startDate, String endDate, MultipartFile activityThumbnail, MultipartFile activityPhoto) {
        Activity activity = null;
        if (id != null) {
            activity = findActivityById(id);
        } else {
            activity = new Activity();
            activity.setCreateTime(System.currentTimeMillis());
        }
        activity.setCaption(caption);
        activity.setDescription(description);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setUpdateTime(System.currentTimeMillis());
        try {
            String originalName;
            String suffix;
            String md5Name;
            if (activityThumbnail != null) {
                originalName = activityThumbnail.getOriginalFilename();
                suffix = originalName.substring(originalName.lastIndexOf("."));
                md5Name = MD5Util.getMd5ByFile(activityThumbnail.getBytes()) + suffix;
                Photo thumbnail = photoRepository.findPhotoByMd5Name(md5Name);
                if (thumbnail == null) {
                    FileCopyUtils.copy(activityThumbnail.getBytes(), new File(config.getPhotoPath() + md5Name));
                    thumbnail = new Photo();
                    thumbnail.setMd5Name(md5Name);
                    thumbnail.setName(originalName);
                    photoRepository.save(thumbnail);
                }
                activity.setThumbnailPhoto(thumbnail);
            }
            if (activityPhoto != null) {
                originalName = activityPhoto.getOriginalFilename();
                suffix = originalName.substring(originalName.lastIndexOf("."));
                md5Name = MD5Util.getMd5ByFile(activityPhoto.getBytes()) + suffix;
                Photo bigPhoto = photoRepository.findPhotoByMd5Name(md5Name);
                if (bigPhoto == null) {
                    FileCopyUtils.copy(activityPhoto.getBytes(), new File(config.getPhotoPath() + md5Name));
                    bigPhoto = new Photo();
                    bigPhoto.setName(originalName);
                    bigPhoto.setMd5Name(md5Name);
                    photoRepository.save(bigPhoto);
                }
                activity.setDetailPhoto(bigPhoto);
            }
            return activityRepository.save(activity);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Activity> listActivity() {
        return activityRepository.findAll(new Sort(Sort.Direction.DESC, "updateTime"));
    }

    @Override
    public ActivityView findActivityContent(Integer id) {
        Activity activity = activityRepository.getOne(id);
        ActivityView av = new ActivityView();
        av.setImageName(activity.getDetailPhoto().getMd5Name());
        av.setDescription(activity.getDescription());
        return av;
    }

    @Override
    public Activity findActivityById(Integer id) {
        return activityRepository.getOne(id);
    }
}

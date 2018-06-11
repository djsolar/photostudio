package com.zhxy.photostudio.controller;

import com.zhxy.photostudio.domain.Activity;
import com.zhxy.photostudio.domain.PhotoAlbum;
import com.zhxy.photostudio.service.ActivityService;
import com.zhxy.photostudio.service.AlbumPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private AlbumPhotoService albumPhotoService;

    @RequestMapping(value = {"/", "/index"})
    public String home(Model model) {
        List<Activity> activityList = activityService.listValidActivity();
        List<PhotoAlbum> photoAlbums = albumPhotoService.listTopPhotoAlbum();
        model.addAttribute("activities", activityList);
        model.addAttribute("photoAlbums", photoAlbums);
        return "home";
    }
}

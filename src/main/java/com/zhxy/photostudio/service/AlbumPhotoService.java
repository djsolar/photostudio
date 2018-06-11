package com.zhxy.photostudio.service;

import com.zhxy.photostudio.domain.PhotoAlbum;
import com.zhxy.photostudio.util.FileMeta;
import com.zhxy.photostudio.util.PhotoAlbumView;
import com.zhxy.photostudio.util.ResponseBean;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface AlbumPhotoService {

    ResponseBean<String> upload(Integer id, String caption, String category, String description, MultipartFile photoThumbnail, MultipartFile photo);

    PhotoAlbum save(PhotoAlbum photoAlbum);

    void delete(Integer id);

    int top(Integer id, Boolean top);

    List<PhotoAlbum> listPhotoAlbum();

    List<PhotoAlbum> listTopPhotoAlbum();

    PhotoAlbumView findPhotoAlbumById(Integer id);

}

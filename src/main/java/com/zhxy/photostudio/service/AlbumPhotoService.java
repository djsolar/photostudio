package com.zhxy.photostudio.service;

import com.zhxy.photostudio.domain.PhotoAlbum;
import com.zhxy.photostudio.util.FileMeta;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface AlbumPhotoService {

    List<FileMeta> upload(MultipartHttpServletRequest request);

    PhotoAlbum save(PhotoAlbum photoAlbum);

    void delete(Integer id);

    List<PhotoAlbum> listPhotoAlbum();

}

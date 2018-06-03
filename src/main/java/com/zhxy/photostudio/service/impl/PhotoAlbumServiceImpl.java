package com.zhxy.photostudio.service.impl;

import com.zhxy.photostudio.domain.PhotoAlbum;
import com.zhxy.photostudio.repository.PhotoAlbumRepository;
import com.zhxy.photostudio.service.AlbumPhotoService;
import com.zhxy.photostudio.util.Config;
import com.zhxy.photostudio.util.FileMeta;
import com.zhxy.photostudio.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class PhotoAlbumServiceImpl implements AlbumPhotoService {

    @Autowired
    private PhotoAlbumRepository photoAlbumRepository;

    @Autowired
    private Config config;
    @Override
    public List<FileMeta> upload(MultipartHttpServletRequest request) {
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf;
        while (itr.hasNext()) {
            String fileName = itr.next();
            mpf = request.getFile(fileName);
            try {
                String md5Name = MD5Util.getMd5ByFile(mpf.getBytes());
                String originalName = mpf.getOriginalFilename();
                String suffix = originalName.substring(originalName.lastIndexOf("."));
                FileCopyUtils.copy(mpf.getBytes(), new File(config.getPhotoPath() + md5Name + suffix));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public PhotoAlbum save(PhotoAlbum photoAlbum) {
        return photoAlbumRepository.save(photoAlbum);
    }

    @Override
    public void delete(Integer id) {
        photoAlbumRepository.deleteById(id);
    }

    @Override
    public List<PhotoAlbum> listPhotoAlbum() {
        return photoAlbumRepository.findAll(new Sort(Sort.Direction.DESC, "updateTime"));
    }
}

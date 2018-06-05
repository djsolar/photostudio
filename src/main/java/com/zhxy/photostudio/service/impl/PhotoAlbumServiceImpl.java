package com.zhxy.photostudio.service.impl;

import com.zhxy.photostudio.domain.Photo;
import com.zhxy.photostudio.domain.PhotoAlbum;
import com.zhxy.photostudio.repository.PhotoAlbumRepository;
import com.zhxy.photostudio.repository.PhotoRepository;
import com.zhxy.photostudio.service.AlbumPhotoService;
import com.zhxy.photostudio.util.Config;
import com.zhxy.photostudio.util.FileMeta;
import com.zhxy.photostudio.util.MD5Util;
import com.zhxy.photostudio.util.ResponseBean;
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
    private PhotoRepository photoRepository;

    @Autowired
    private Config config;

    /*@Override
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
    }*/

    @Override
    public ResponseBean<String> upload(String caption, String category, String description, MultipartFile photoThumbnail, MultipartFile photo) {
        PhotoAlbum photoAlbum = new PhotoAlbum();
        photoAlbum.setCaption(caption);
        photoAlbum.setCategory(category);
        photoAlbum.setDescription(description);
        try {

            String originalName = photoThumbnail.getOriginalFilename();
            String suffix = originalName.substring(originalName.lastIndexOf("."));
            String md5Name = MD5Util.getMd5ByFile(photoThumbnail.getBytes()) + suffix;
            Photo thumbnail = photoRepository.findPhotoByMd5Name(md5Name);
            if (thumbnail == null) {
                FileCopyUtils.copy(photoThumbnail.getBytes(), new File(config.getPhotoPath() + md5Name));
                thumbnail = new Photo();
                thumbnail.setMd5Name(md5Name);
                thumbnail.setName(originalName);
                photoRepository.save(thumbnail);
            }
            originalName = photo.getOriginalFilename();
            suffix = originalName.substring(originalName.lastIndexOf("."));
            md5Name = MD5Util.getMd5ByFile(photo.getBytes()) + suffix;
            Photo bigPhoto = photoRepository.findPhotoByMd5Name(md5Name);
            if (bigPhoto == null) {
                FileCopyUtils.copy(photo.getBytes(), new File(config.getPhotoPath() + md5Name));
                bigPhoto = new Photo();
                bigPhoto.setName(originalName);
                bigPhoto.setMd5Name(md5Name);
                photoRepository.save(bigPhoto);
            }
            photoAlbum.setThumbnailPhoto(thumbnail);
            photoAlbum.setContentPhoto(bigPhoto);
            photoAlbumRepository.save(photoAlbum);
            return new ResponseBean<>(true);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseBean<>(null, false, e.getMessage());
        }
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

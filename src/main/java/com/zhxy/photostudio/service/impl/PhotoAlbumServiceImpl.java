package com.zhxy.photostudio.service.impl;

import com.zhxy.photostudio.domain.Photo;
import com.zhxy.photostudio.domain.PhotoAlbum;
import com.zhxy.photostudio.repository.PhotoAlbumRepository;
import com.zhxy.photostudio.repository.PhotoRepository;
import com.zhxy.photostudio.service.AlbumPhotoService;
import com.zhxy.photostudio.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PhotoAlbumServiceImpl implements AlbumPhotoService {

    @Autowired
    private PhotoAlbumRepository photoAlbumRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private Config config;

    @Override
    public ResponseBean<String> upload(Integer id, String caption, String category, String description, MultipartFile photoThumbnail, MultipartFile photo) {
        PhotoAlbum photoAlbum = new PhotoAlbum();
        photoAlbum.setCaption(caption);
        photoAlbum.setCategory(category);
        photoAlbum.setDescription(description);
        photoAlbum.setDeleted(false);
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
            photoAlbum.setTop(false);
            if (id == null) {
                photoAlbum.setCreateTime(System.currentTimeMillis());
            }
            photoAlbum.setUpdateTime(System.currentTimeMillis());
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
        photoAlbumRepository.deletePhotoAlbum(id);
    }

    @Override
    public int top(Integer id, Boolean top) {
        return photoAlbumRepository.updateTop(id, top);
    }

    @Override
    public List<PhotoAlbum> listPhotoAlbum() {
        Specification<PhotoAlbum> specification = new Specification<PhotoAlbum>() {
            @Override
            public Predicate toPredicate(Root<PhotoAlbum> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("deleted").as(Boolean.class), false);
            }
        };
        return photoAlbumRepository.findAll(specification, new Sort(Sort.Direction.DESC, "top", "updateTime"));
    }

    @Override
    public PhotoAlbumView findPhotoAlbumById(Integer id) {
        Optional<PhotoAlbum> photoAlbumOptional = photoAlbumRepository.findById(id);
        if (photoAlbumOptional.isPresent()) {
            PhotoAlbum photoAlbum = photoAlbumOptional.get();
            PhotoAlbumView photoAlbumView = new PhotoAlbumView();
            photoAlbumView.setImageName(photoAlbum.getContentPhoto().getMd5Name());
            photoAlbumView.setDescription(photoAlbum.getDescription());
            return photoAlbumView;
        }
        return null;
    }
}

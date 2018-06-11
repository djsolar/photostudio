package com.zhxy.photostudio.service.impl;

import com.zhxy.photostudio.domain.Role;
import com.zhxy.photostudio.domain.User;
import com.zhxy.photostudio.repository.RoleRepository;
import com.zhxy.photostudio.repository.UserRepository;
import com.zhxy.photostudio.service.UserService;
import com.zhxy.photostudio.util.Config;
import com.zhxy.photostudio.util.DataTableViewPage;
import com.zhxy.photostudio.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.File;
import java.io.IOException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private Config config;

    @Override
    public User addAdmin() {
        User user = userRepository.findUserByAdmin(1);
        if (user == null) {
            user = new User();
            user.setAvatar("/images/img.jpg");
            user.setNickName("admin");
            user.setUsername("admin");
            user.setPassword(bCryptPasswordEncoder.encode("123456"));
            user.setAdmin(1);
            Role role = roleRepository.findByName("管理员");
            user.setRole(role);
            return userRepository.save(user);
        } else {
            return user;
        }
    }

    @Override
    public User findAdmin() {
        return userRepository.findUserByAdmin(1);
    }

    @Override
    public DataTableViewPage<User> listUser(int page, int pageSize, String searchValue) {
        Page<User> userPage;
        if (StringUtils.isEmpty(searchValue)) {
            userPage = userRepository.findAll(PageRequest.of(page, pageSize));
        } else {
            Specification<User> customerSpecification = new Specification<User>() {

                @Override
                public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    Predicate p1 = cb.like(root.get("nickName").as(String.class), "%" + searchValue + "%");
                    Predicate p2 = cb.like(root.get("username").as(String.class), "%" + searchValue + "%");
                    return cb.or(p1, p2);
                }
            };
            userPage = userRepository.findAll(customerSpecification, PageRequest.of(page, pageSize));

        }
        DataTableViewPage<User> dataTableViewPage = new DataTableViewPage<>();;
        dataTableViewPage.setAaData(userPage.getContent());
        dataTableViewPage.setRecordsTotal(userPage.getTotalElements());
        dataTableViewPage.setRecordsFiltered(userPage.getTotalElements());
        return dataTableViewPage;
    }

    @Override
    public User saveUser(User user, Integer roleId) {

        User user1;
        if (user.getId() != null) {
            user1 = userRepository.getOne(user.getId());
            if (user1.getAdmin() == 1) {
                return null;
            }
            user1.setNickName(user.getNickName());
            user1.setUsername(user.getUsername());
            Role role = roleRepository.getOne(roleId);
            user1.setRole(role);
            return userRepository.save(user1);
        } else {
            user.setAdmin(0);
            Role role = roleRepository.getOne(roleId);
            user.setRole(role);
            user.setAvatar("/images/img.jpg");
            user.setPassword(bCryptPasswordEncoder.encode("123456"));
            return userRepository.save(user);
        }
    }

    @Override
    public User updateUser(String username, String nickName, String password, String newPassword, String confirmPassword, MultipartFile avatar) {
        User user = userRepository.findUserByUsername(username);
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return null;
        }
        if (StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(confirmPassword) || !newPassword.equals(confirmPassword)) {
            return null;
        }
        if (avatar != null) {
            String originalName = avatar.getOriginalFilename();
            String suffix = originalName.substring(originalName.lastIndexOf("."));
            try {
                String md5Name = MD5Util.getMd5ByFile(avatar.getBytes()) + suffix;
                FileCopyUtils.copy(avatar.getBytes(), new File(config.getPhotoPath() + md5Name));
                user.setAvatar("/photo/" + md5Name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        user.setNickName(nickName);
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    @Override
    public boolean deleteUser(Integer id) {
        User user = userRepository.getOne(id);
        if (user.getAdmin() == 1) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean resetUser(Integer id) {
        User user = userRepository.getOne(id);
        if (user.getAdmin() == 1) {
            return false;
        }
        user.setPassword(bCryptPasswordEncoder.encode("123456"));
        userRepository.save(user);
        return true;
    }
}

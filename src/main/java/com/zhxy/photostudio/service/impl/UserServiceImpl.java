package com.zhxy.photostudio.service.impl;

import com.zhxy.photostudio.domain.Role;
import com.zhxy.photostudio.domain.User;
import com.zhxy.photostudio.repository.RoleRepository;
import com.zhxy.photostudio.repository.UserRepository;
import com.zhxy.photostudio.service.UserService;
import com.zhxy.photostudio.util.DataTableViewPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
        User user1 = userRepository.findUserByUsername(user.getUsername());
        if (user1 != null)
            return null;
        user.setAdmin(0);
        Role role = roleRepository.getOne(roleId);
        user.setRole(role);
        user.setAvatar("/images/img.jpg");
        user.setPassword(bCryptPasswordEncoder.encode("123456"));
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
    public void resetUser(Integer id) {
        User user = userRepository.getOne(id);
        user.setPassword(bCryptPasswordEncoder.encode("123456"));
        userRepository.save(user);
    }
}

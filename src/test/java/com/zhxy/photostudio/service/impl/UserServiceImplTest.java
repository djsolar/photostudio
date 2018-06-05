package com.zhxy.photostudio.service.impl;

import com.zhxy.photostudio.domain.Authority;
import com.zhxy.photostudio.domain.Role;
import com.zhxy.photostudio.domain.User;
import com.zhxy.photostudio.repository.AuthorityRepository;
import com.zhxy.photostudio.repository.RoleRepository;
import com.zhxy.photostudio.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;


@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private RoleRepository roleRepository;

  /*  @Test
    public void loadUserByUsername() {
        UserDetails myUser = userService.loadUserByUsername("admin");
        System.out.println(myUser.getPassword());
        System.out.println(myUser.toString());
    }*/

    @Test
    public void addAdmin() {
        String[] authoritys = new String[] {"a1", "a2", "a3", "a4"};
        Set<Authority> authorities = new HashSet<>();
        for(int i = 0; i < authoritys.length; i++) {
            String authority = authoritys[i];
            Authority a = new Authority();
            a.setName(authority);
            a.setCode(i);
            authorities.add(a);
        }
        authorityRepository.saveAll(authorities);

        Role role = new Role();
        role.setName("店长");
        role.setAuthorities(authorities);

        roleRepository.save(role);

        User user = new User();
        user.setUsername("admin");
        user.setPassword("123456");
        user.setRole(role);
        user.setAvatar("/images/img.jpg");
        user.setNickName("djsolar");
        userService.addAdmin(user);
    }
}
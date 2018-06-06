package com.zhxy.photostudio;

import com.zhxy.photostudio.service.RoleService;
import com.zhxy.photostudio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class DbInitialRunner implements ApplicationRunner{

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        roleService.addAdminRole();
        userService.addAdmin();
    }
}

package com.zhxy.photostudio.service;

import com.zhxy.photostudio.domain.User;
import com.zhxy.photostudio.util.DataTableViewPage;

public interface UserService {

    User addAdmin();

    User findAdmin();

    DataTableViewPage<User> listUser(int page, int pageSize, String searchValue);

    User saveUser(User user, Integer roleId);

    boolean deleteUser(Integer id);

    void resetUser(Integer id);
}

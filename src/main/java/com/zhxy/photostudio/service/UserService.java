package com.zhxy.photostudio.service;

import com.zhxy.photostudio.domain.User;
import com.zhxy.photostudio.util.DataTableViewPage;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    User addAdmin();

    User findAdmin();

    DataTableViewPage<User> listUser(int page, int pageSize, String searchValue);

    User saveUser(User user, Integer roleId);

    User updateUser(String username, String nickName, String password, String newPassword, String confirmPassword, MultipartFile avatar);

    boolean deleteUser(Integer id);

    boolean resetUser(Integer id);
}

package com.zhxy.photostudio.service;

import com.zhxy.photostudio.domain.Role;
import com.zhxy.photostudio.util.DataTableViewPage;

import java.util.List;

public interface RoleService {

    void addAdminRole();

    DataTableViewPage<Role> listRole(int page, int pageSize, String searchValue);

    List<Role> findAll();

    void save(Integer id, String roleName, Integer[] authorities);

    boolean delete(Integer id);

    Role findOne(Integer id);
}

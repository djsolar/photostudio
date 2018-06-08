package com.zhxy.photostudio.service.impl;

import com.zhxy.photostudio.domain.Authority;
import com.zhxy.photostudio.domain.Role;
import com.zhxy.photostudio.domain.User;
import com.zhxy.photostudio.repository.AuthorityRepository;
import com.zhxy.photostudio.repository.RoleRepository;
import com.zhxy.photostudio.repository.UserRepository;
import com.zhxy.photostudio.service.RoleService;
import com.zhxy.photostudio.util.DataTableViewPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addAdminRole() {

        String[] authorities = new String[] {
                "添加客户", "修改客户", "删除客户", "查看订单", "添加订单", "修改订单", "添加活动", "修改活动", "删除活动","查看活动", "添加套系", "修改套系", "删除套系",
                "删除套系", "添加商品", "修改商品", "删除商品", "添加相册", "查看相册", "置顶相册", "删除相册", "添加角色", "修改角色", "删除角色",
                "添加用户", "修改用户", "重置用户", "删除用户"
        };
        for(int i = 0; i < authorities.length; i++) {
            String authorityName = authorities[i];
            Authority authority = authorityRepository.findByName(authorityName);
            if (authority == null) {
                authority = new Authority();
                authority.setName(authorityName);
                authority.setCode(i);
                authorityRepository.save(authority);
            }
        }
        Role role = roleRepository.findByName("管理员");
        if (role == null) {
            role = new Role();
            role.setName("管理员");
            role.setCreateTime(System.currentTimeMillis());
            role.setUpdateTime(System.currentTimeMillis());
            Set<Authority> authorities1 = new HashSet<>(authorityRepository.findAll());
            role.setAuthorities(authorities1);
            roleRepository.save(role);
        }

    }

    @Override
    public DataTableViewPage<Role> listRole(int page, int pageSize, String searchValue) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Page<Role> rolePage;
        if (StringUtils.isEmpty(searchValue)) {
            rolePage = roleRepository.findAll(PageRequest.of(page, pageSize, sort));
        } else {
            Specification<Role> customerSpecification = new Specification<Role>() {

                @Override
                public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    Predicate p1 = cb.like(root.get("name").as(String.class), "%" + searchValue + "%");
                    return p1;
                }
            };
            rolePage = roleRepository.findAll(customerSpecification, PageRequest.of(page, pageSize, sort));

        }
        DataTableViewPage<Role> dataTableViewPage = new DataTableViewPage<>();;
        dataTableViewPage.setAaData(rolePage.getContent());
        dataTableViewPage.setRecordsTotal(rolePage.getTotalElements());
        dataTableViewPage.setRecordsFiltered(rolePage.getTotalElements());
        return dataTableViewPage;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public void save(Integer id, String roleName, Integer[] authorities) {
        Role role;
        if (id != null) {
            role = roleRepository.getOne(id);
            if (role.getName().equals("管理员")) {
                return;
            }
            role.setUpdateTime(System.currentTimeMillis());
        } else {
            role = new Role();
            role.setUpdateTime(System.currentTimeMillis());
            role.setCreateTime(System.currentTimeMillis());
        }
        Set<Authority> authorities1 = new HashSet<>();
        for(Integer code : authorities) {
            authorities1.add(authorityRepository.findAuthorityByCode(code));
        }
        role.setName(roleName);
        role.setAuthorities(authorities1);
        roleRepository.save(role);
    }

    @Override
    public boolean delete(Integer id) {
        Role role = roleRepository.getOne(id);
        if (role.getName().equals("管理员")) {
            return false;
        }
        List<User> users = userRepository.findUsersByRole(role);
        if (users != null && users.size() > 0) {
            return false;
        }
        roleRepository.delete(role);
        return  true;
    }

    @Override
    public Role findOne(Integer id) {
        return roleRepository.getOne(id);
    }
}

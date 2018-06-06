package com.zhxy.photostudio.repository;

import com.zhxy.photostudio.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    User findUserByUsername(String userName);

    User findUserByAdmin(Integer admin);
}

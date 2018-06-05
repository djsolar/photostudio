package com.zhxy.photostudio.repository;

import com.zhxy.photostudio.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByUsername(String userName);
}

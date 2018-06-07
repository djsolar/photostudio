package com.zhxy.photostudio.service.impl;

import com.zhxy.photostudio.domain.Authority;
import com.zhxy.photostudio.repository.AuthorityRepository;
import com.zhxy.photostudio.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public List<Authority> findAll() {
        return authorityRepository.findAll();
    }
}

package com.zhxy.photostudio.service.impl;

import com.zhxy.photostudio.domain.Commodity;
import com.zhxy.photostudio.repository.CommodityRepository;
import com.zhxy.photostudio.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    private CommodityRepository commodityRepository;

    @Override
    public Commodity saveCommodity(Commodity commodity) {
        commodity.setCreateTime(System.currentTimeMillis());
        commodity.setUpdateTime(System.currentTimeMillis());
        return commodityRepository.saveAndFlush(commodity);
    }
}

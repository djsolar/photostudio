package com.zhxy.photostudio.repository;

import com.zhxy.photostudio.domain.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CommodityRepository extends JpaRepository<Commodity, Integer>, JpaSpecificationExecutor<Commodity>{
}

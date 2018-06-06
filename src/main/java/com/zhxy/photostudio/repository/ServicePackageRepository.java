package com.zhxy.photostudio.repository;

import com.zhxy.photostudio.domain.ServicePackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ServicePackageRepository extends JpaRepository<ServicePackage, Integer>, JpaSpecificationExecutor<ServicePackage> {

    @Modifying
    @Query("update ServicePackage set deleted=true where id=:id")
    int deleteServicePackage(@Param("id") Integer id);
}

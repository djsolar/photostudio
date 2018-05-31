package com.zhxy.photostudio.repository;

import com.zhxy.photostudio.domain.ServicePackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServicePackageRepository extends JpaRepository<ServicePackage, Integer>, JpaSpecificationExecutor<ServicePackage> {
}

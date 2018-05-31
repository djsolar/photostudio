package com.zhxy.photostudio.service;

import com.zhxy.photostudio.domain.ServicePackage;
import com.zhxy.photostudio.util.DataTableViewPage;

public interface ServicePackageService {

    ServicePackage save(ServicePackage servicePackage);

    DataTableViewPage<ServicePackage> listService(int page, int pageSize, String searchValue);

    void delete(Integer id);
}

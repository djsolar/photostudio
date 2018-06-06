package com.zhxy.photostudio.service;

import com.zhxy.photostudio.domain.ServicePackage;
import com.zhxy.photostudio.util.DataTableViewPage;
import com.zhxy.photostudio.util.ServiceView;

import java.util.List;

public interface ServicePackageService {

    ServicePackage save(ServicePackage servicePackage);

    DataTableViewPage<ServicePackage> listService(int page, int pageSize, String searchValue);

    List<ServiceView> listAllService();

    void delete(Integer id);
}

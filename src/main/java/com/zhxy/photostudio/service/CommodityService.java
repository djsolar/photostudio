package com.zhxy.photostudio.service;

import com.zhxy.photostudio.domain.Commodity;
import com.zhxy.photostudio.util.DataTableViewPage;

public interface CommodityService {

    Commodity saveCommodity(Commodity commodity);

    void deleteCommodity(Integer id);

    DataTableViewPage<Commodity> listCommodity(int page, int pageSize, String searchValue);
}

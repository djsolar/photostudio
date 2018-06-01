package com.zhxy.photostudio.service;

import com.zhxy.photostudio.domain.Order;
import com.zhxy.photostudio.util.DataTableViewPage;

public interface OrderService {

    Order saveOrder(Order order);

    void delete(Integer id);

    DataTableViewPage<Order> listOrder(int page, int pageSize, String searchValue);
}

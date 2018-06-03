package com.zhxy.photostudio.service;

import com.zhxy.photostudio.domain.Customer;
import com.zhxy.photostudio.domain.Order;
import com.zhxy.photostudio.util.DataTableViewPage;
import com.zhxy.photostudio.util.OrderView;

public interface OrderService {

    Order saveOrder(Order order, Integer customerId, Integer serviceId);

    void delete(Integer id);

    OrderView getOrder(Integer id);

    DataTableViewPage<OrderView> listOrder(int page, int pageSize, String searchValue);
}

package com.zhxy.photostudio.service;

import com.zhxy.photostudio.domain.Customer;
import com.zhxy.photostudio.util.CustomerView;
import com.zhxy.photostudio.util.DataTableViewPage;

public interface CustomerService {

    DataTableViewPage<Customer> listCustomer(int page, int length, String searchValue);

    Customer save(Customer customer);

    void delete(Integer id);
}

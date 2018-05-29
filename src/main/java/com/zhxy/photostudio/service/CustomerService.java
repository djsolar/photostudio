package com.zhxy.photostudio.service;

import com.zhxy.photostudio.domain.Customer;
import com.zhxy.photostudio.util.DataTableViewPage;

public interface CustomerService {

    DataTableViewPage<Customer> findAll();

    Customer save(Customer customer);

    void delete(Integer id);

}

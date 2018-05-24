package com.zhxy.photostudio.service;

import com.zhxy.photostudio.domain.Customer;
import com.zhxy.photostudio.util.EntityPage;

import java.util.List;

public interface CustomerService {

    EntityPage<Customer> findAll();

    Customer save(Customer customer);

    void delete(Integer id);

}

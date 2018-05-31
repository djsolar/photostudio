package com.zhxy.photostudio.service.impl;

import com.zhxy.photostudio.domain.Customer;
import com.zhxy.photostudio.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerServiceImplTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void findAll() {

    }

    @Test
    public void save() {
        for(int i = 0; i < 30; i++) {
            Customer customer = new Customer();
            customer.setManName("zhouyiran" + i);
            customer.setWomenName("chaoyue" + i);
            customer.setPhone("15712872575");
            customer.setAddress("Beijing");
            customer.setCreateTime(System.currentTimeMillis());
            customer.setUpdateTime(System.currentTimeMillis());
            customerService.save(customer);
        }
    }

    @Test
    public void delete() {

    }
}
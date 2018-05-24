package com.zhxy.photostudio.service.impl;

import com.zhxy.photostudio.domain.Customer;
import com.zhxy.photostudio.repository.CustomerRepository;
import com.zhxy.photostudio.service.CustomerService;
import com.zhxy.photostudio.util.EntityPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public EntityPage<Customer> findAll() {
        List<Customer> customers = customerRepository.findAll();
        EntityPage<Customer> entityPage = new EntityPage<>();
        entityPage.setData(customers);
        entityPage.setDraw(1);
        entityPage.setRecordsTotal(customers.size());
        entityPage.setRecordsFiltered(customers.size());
        return entityPage;
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void delete(Integer id) {
        customerRepository.deleteById(id);
    }
}

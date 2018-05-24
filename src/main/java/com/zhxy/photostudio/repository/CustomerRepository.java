package com.zhxy.photostudio.repository;

import com.zhxy.photostudio.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}

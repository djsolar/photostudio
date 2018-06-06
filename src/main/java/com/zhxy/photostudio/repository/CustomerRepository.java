package com.zhxy.photostudio.repository;

import com.zhxy.photostudio.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Integer>, JpaSpecificationExecutor<Customer>{

    @Modifying
    @Query("update Customer set deleted = true where id = :id")
    int deleteCustomer(@Param("id") Integer id);
}

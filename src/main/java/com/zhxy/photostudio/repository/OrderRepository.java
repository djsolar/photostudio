package com.zhxy.photostudio.repository;

import com.zhxy.photostudio.domain.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}

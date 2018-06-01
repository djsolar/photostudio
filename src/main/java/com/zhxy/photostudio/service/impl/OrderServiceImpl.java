package com.zhxy.photostudio.service.impl;

import com.zhxy.photostudio.domain.Customer;
import com.zhxy.photostudio.domain.Order;
import com.zhxy.photostudio.repository.OrderRepository;
import com.zhxy.photostudio.service.OrderService;
import com.zhxy.photostudio.util.DataTableViewPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order saveOrder(Order order) {
        if (order.getId() == null) {
            order.setCreateTime(System.currentTimeMillis());
        }
        order.setUpdateTime(System.currentTimeMillis());
        return orderRepository.save(order);
    }

    @Override
    public void delete(Integer id) {
        orderRepository.deleteById(id);
    }

    @Override
    public DataTableViewPage<Order> listOrder(int page, int pageSize, String searchValue) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Page<Order> orderPage;
        if (StringUtils.isEmpty(searchValue)) {
            orderPage = orderRepository.findAll(PageRequest.of(page, pageSize, sort));
        } else {
            Specification<Order> orderSpecification = new Specification<Order>() {

                @Override
                public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    Predicate p1 = cb.like(root.join("customer").get("manName").as(String.class), "%" + searchValue + "%");
                    Predicate p2 = cb.like(root.join("customer").get("womenName").as(String.class), "%" + searchValue + "%");
                    Predicate p3 = cb.like(root.join("customer").get("phone").as(String.class), "%" + searchValue + "%");
                    Predicate p4 = cb.like(root.join("customer").get("address").as(String.class), "%" + searchValue + "%");
                    return cb.or(p1, p2, p3, p4);
                }
            };
            orderPage = orderRepository.findAll(orderSpecification, PageRequest.of(page, pageSize, sort));

        }
        DataTableViewPage<Order> dataTableViewPage = new DataTableViewPage<>();;
        dataTableViewPage.setAaData(orderPage.getContent());
        dataTableViewPage.setRecordsTotal(orderPage.getTotalElements());
        dataTableViewPage.setRecordsFiltered(orderPage.getTotalElements());
        return dataTableViewPage;
    }
}

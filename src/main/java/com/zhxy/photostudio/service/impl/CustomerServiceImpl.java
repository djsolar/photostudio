package com.zhxy.photostudio.service.impl;

import com.zhxy.photostudio.domain.Customer;
import com.zhxy.photostudio.domain.Order;
import com.zhxy.photostudio.repository.CustomerRepository;
import com.zhxy.photostudio.repository.OrderRepository;
import com.zhxy.photostudio.service.CustomerService;
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
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public DataTableViewPage<Customer> listCustomer(int page, int pageSize, String searchValue) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Page<Customer> customerPage;
        if (StringUtils.isEmpty(searchValue)) {
            customerPage = customerRepository.findAll(PageRequest.of(page, pageSize, sort));
        } else {
            Specification<Customer> customerSpecification = new Specification<Customer>() {

                @Override
                public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    Predicate p1 = cb.like(root.get("manName").as(String.class), "%" + searchValue + "%");
                    Predicate p2 = cb.like(root.get("womenName").as(String.class), "%" + searchValue + "%");
                    Predicate p3 = cb.like(root.get("phone").as(String.class), "%" + searchValue + "%");
                    Predicate p4 = cb.like(root.get("address").as(String.class), "%" + searchValue + "%");
                    Predicate p5 = cb.equal(root.get("deleted").as(Boolean.class), false);
                    return cb.or(p1, p2, p3, p4);
                }
            };
            customerPage = customerRepository.findAll(customerSpecification, PageRequest.of(page, pageSize, sort));

        }
        DataTableViewPage<Customer> dataTableViewPage = new DataTableViewPage<>();;
        dataTableViewPage.setAaData(customerPage.getContent());
        dataTableViewPage.setRecordsTotal(customerPage.getTotalElements());
        dataTableViewPage.setRecordsFiltered(customerPage.getTotalElements());
        return dataTableViewPage;
    }

    @Override
    public Customer save(Customer customer) {
        if (customer.getId() == null) {
            customer.setCreateTime(System.currentTimeMillis());
        }
        customer.setUpdateTime(System.currentTimeMillis());
        return customerRepository.save(customer);
    }

    @Override
    public void delete(Integer id) {
        Customer customer = customerRepository.getOne(id);
        customerRepository.deleteCustomer(id);
        Order order = customer.getOrder();
        if (order != null) {
            orderRepository.deleteOrder(order.getId());
        }
    }
}

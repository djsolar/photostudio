package com.zhxy.photostudio.service.impl;

import com.zhxy.photostudio.domain.Customer;
import com.zhxy.photostudio.domain.Order;
import com.zhxy.photostudio.domain.ServicePackage;
import com.zhxy.photostudio.repository.CustomerRepository;
import com.zhxy.photostudio.repository.OrderRepository;
import com.zhxy.photostudio.repository.ServicePackageRepository;
import com.zhxy.photostudio.service.OrderService;
import com.zhxy.photostudio.util.CustomerView;
import com.zhxy.photostudio.util.DataTableViewPage;
import com.zhxy.photostudio.util.OrderView;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ServicePackageRepository servicePackageRepository;

    @Override
    public Order saveOrder(Order order, Integer customerId, Integer serviceId) {
        order.setDeleted(false);
        ServicePackage servicePackage = servicePackageRepository.getOne(serviceId);
        if (order.getId() != null) {
            Order oldOrder = orderRepository.getOne(order.getId());
            oldOrder.setServicePackage(servicePackage);
            oldOrder.setEarnest(order.getEarnest());
            oldOrder.setUpdateTime(System.currentTimeMillis());
            oldOrder.setPickPhotoTime(order.getPickPhotoTime());
            oldOrder.setTakePhotoTime(order.getTakePhotoTime());
            oldOrder.setSelectPhotoTime(order.getSelectPhotoTime());
            oldOrder.setRemark(order.getRemark());
            oldOrder.setPrice(order.getPrice());
            return orderRepository.save(oldOrder);
        } else {
            Customer customer = customerRepository.getOne(customerId);
            order.setCreateTime(System.currentTimeMillis());
            order.setCustomer(customer);
            order.setUpdateTime(System.currentTimeMillis());
            order.setServicePackage(servicePackage);
            return orderRepository.save(order);
        }
    }

    @Override
    public void delete(Integer id) {
        orderRepository.deleteOrder(id);
    }

    @Override
    public OrderView getOrder(Integer id) {
        Order order =  orderRepository.getOne(id);
        Customer customer = order.getCustomer();
        OrderView orderView = new OrderView();
        orderView.setCustomerId(customer.getId());
        orderView.setEarnest(order.getEarnest());
        orderView.setPickPhotoTime(order.getPickPhotoTime());
        orderView.setSelectPhotoTime(order.getSelectPhotoTime());
        orderView.setTakePhotoTime(order.getTakePhotoTime());
        orderView.setPrice(order.getPrice());
        orderView.setRemark(order.getRemark());
        ServicePackage servicePackage = order.getServicePackage();
        orderView.setServiceId(servicePackage.getId());
        orderView.setId(order.getId());
        return orderView;
    }

    @Override
    public DataTableViewPage<OrderView> listOrder(int page, int pageSize, String searchValue) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Page<Order> orderPage;
        if (StringUtils.isEmpty(searchValue)) {
            Specification<Order> specification = new Specification<Order>() {
                @Override
                public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    return criteriaBuilder.equal(root.get("deleted").as(Boolean.class), false);
                }
            };
            orderPage = orderRepository.findAll(specification, PageRequest.of(page, pageSize, sort));
        } else {
            Specification<Order> orderSpecification = new Specification<Order>() {

                @Override
                public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    Predicate p1 = cb.like(root.join("customer").get("manName").as(String.class), "%" + searchValue + "%");
                    Predicate p2 = cb.like(root.join("customer").get("womenName").as(String.class), "%" + searchValue + "%");
                    Predicate p3 = cb.like(root.join("customer").get("phone").as(String.class), "%" + searchValue + "%");
                    Predicate p4 = cb.like(root.join("customer").get("address").as(String.class), "%" + searchValue + "%");
                    Predicate p5 = cb.equal(root.get("deleted").as(Boolean.class), false);
                    return cb.and(p5, cb.or(p1, p2, p3, p4));
                }
            };
            orderPage = orderRepository.findAll(orderSpecification, PageRequest.of(page, pageSize, sort));

        }
        List<OrderView> orderViews = new ArrayList<>();
        for(Order order : orderPage.getContent()) {
            Customer customer = order.getCustomer();
            OrderView orderView = new OrderView();
            orderView.setCustomerId(customer.getId());
            orderView.setEarnest(order.getEarnest());
            orderView.setPickPhotoTime(order.getPickPhotoTime());
            orderView.setSelectPhotoTime(order.getSelectPhotoTime());
            orderView.setTakePhotoTime(order.getTakePhotoTime());
            orderView.setPrice(order.getPrice());
            orderView.setRemark(order.getRemark());
            ServicePackage servicePackage = order.getServicePackage();
            orderView.setServiceId(servicePackage.getId());
            orderView.setId(order.getId());
            orderView.setManName(customer.getManName());
            orderView.setWomenName(customer.getWomenName());
            orderView.setUpdateTime(order.getUpdateTime());
            orderView.setAddress(customer.getAddress());
            orderView.setPhone(customer.getPhone());
            orderView.setServiceName(servicePackage.getName());
            orderViews.add(orderView);
        }
        DataTableViewPage<OrderView> dataTableViewPage = new DataTableViewPage<>();;
        dataTableViewPage.setAaData(orderViews);
        dataTableViewPage.setRecordsTotal(orderViews.size());
        dataTableViewPage.setRecordsFiltered(orderViews.size());
        return dataTableViewPage;
    }

    @Override
    public DataTableViewPage<CustomerView> listTodayCustomer(int page, int length, String searchValue) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date());
        Page<Order> orderPage;
        if (StringUtils.isEmpty(searchValue)) {
            Specification<Order> specification = new Specification<Order>() {
                @Override
                public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Predicate p1 = criteriaBuilder.equal(root.get("deleted").as(Boolean.class), false);
                    Predicate p2 = criteriaBuilder.equal(root.get("takePhotoTime").as(String.class), today);
                    Predicate p3 = criteriaBuilder.equal(root.get("selectPhotoTime").as(String.class), today);
                    Predicate p4 = criteriaBuilder.equal(root.get("pickPhotoTime").as(String.class), today);
                    return criteriaBuilder.and(criteriaBuilder.or(p2, p3, p4));
                }
            };
            orderPage = orderRepository.findAll(specification, PageRequest.of(page, length, sort));
        } else {
            Specification<Order> orderSpecification = new Specification<Order>() {

                @Override
                public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    Predicate p1 = cb.like(root.join("customer").get("manName").as(String.class), "%" + searchValue + "%");
                    Predicate p2 = cb.like(root.join("customer").get("womenName").as(String.class), "%" + searchValue + "%");
                    Predicate p3 = cb.like(root.join("customer").get("phone").as(String.class), "%" + searchValue + "%");
                    Predicate p4 = cb.like(root.join("customer").get("address").as(String.class), "%" + searchValue + "%");
                    Predicate p5 = cb.equal(root.get("deleted").as(Boolean.class), false);
                    Predicate p6 = cb.equal(root.get("takePhotoTime").as(String.class), today);
                    Predicate p7 = cb.equal(root.get("selectPhotoTime").as(String.class), today);
                    Predicate p8 = cb.equal(root.get("pickPhotoTime").as(String.class), today);
                    return cb.and(p5, cb.or(p1, p2, p3, p4), cb.or(p6, p7, p8));
                }
            };
            orderPage = orderRepository.findAll(orderSpecification, PageRequest.of(page, length, sort));
        }
        List<Order> orders = orderPage.getContent();
        List<CustomerView> customerViews = new ArrayList<>();
        for(Order order : orders) {
            CustomerView customerView = new CustomerView();
            customerView.setId(order.getId());
            customerView.setManName(order.getCustomer().getManName());
            customerView.setWomenName(order.getCustomer().getWomenName());
            customerView.setPhone(order.getCustomer().getPhone());
            if (today.equals(order.getTakePhotoTime())) {
                customerView.setEvent("客户今日拍照");
            } else if (today.equals(order.getSelectPhotoTime())) {
                customerView.setEvent("客户今日挑选照片");
            } else if (today.equals(order.getPickPhotoTime())) {
                customerView.setEvent("客户今日取照片");
            }
            customerViews.add(customerView);
        }
        DataTableViewPage<CustomerView> dataTableViewPage = new DataTableViewPage<>();
        dataTableViewPage.setRecordsFiltered(customerViews.size());
        dataTableViewPage.setRecordsTotal(customerViews.size());
        dataTableViewPage.setAaData(customerViews);
        return dataTableViewPage;
    }
}

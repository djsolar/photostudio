package com.zhxy.photostudio.service.impl;

import com.zhxy.photostudio.domain.ServicePackage;
import com.zhxy.photostudio.repository.ServicePackageRepository;
import com.zhxy.photostudio.service.ServicePackageService;
import com.zhxy.photostudio.util.DataTableViewPage;
import com.zhxy.photostudio.util.ServiceView;
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
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicePackageServiceImpl implements ServicePackageService {

    @Autowired
    private ServicePackageRepository servicePackageRepository;

    @Override
    public ServicePackage save(ServicePackage servicePackage) {
        if (servicePackage.getId() == null) {
            servicePackage.setCreateTime(System.currentTimeMillis());
        }
        servicePackage.setUpdateTime(System.currentTimeMillis());
        return servicePackageRepository.save(servicePackage);
    }

    @Override
    public DataTableViewPage<ServicePackage> listService(int page, int pageSize, String searchValue) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Page<ServicePackage> servicePackagePage;
        if (StringUtils.isEmpty(searchValue)) {
            servicePackagePage = servicePackageRepository.findAll(PageRequest.of(page, pageSize, sort));
        } else {
            Specification<ServicePackage> customerSpecification = new Specification<ServicePackage>() {

                @Override
                public Predicate toPredicate(Root<ServicePackage> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    Predicate p1 = cb.like(root.get("name").as(String.class), "%" + searchValue + "%");
                    Predicate p2 = cb.like(root.get("remark").as(String.class), "%" + searchValue + "%");
                    return cb.or(p1, p2);
                }
            };
            servicePackagePage = servicePackageRepository.findAll(customerSpecification, PageRequest.of(page, pageSize, sort));

        }
        DataTableViewPage<ServicePackage> dataTableViewPage = new DataTableViewPage<>();;
        dataTableViewPage.setAaData(servicePackagePage.getContent());
        dataTableViewPage.setRecordsTotal(servicePackagePage.getTotalElements());
        dataTableViewPage.setRecordsFiltered(servicePackagePage.getTotalElements());
        return dataTableViewPage;
    }

    @Override
    public List<ServiceView> listAllService() {
        List<ServicePackage> servicePackages = servicePackageRepository.findAll();
        List<ServiceView> serviceViews = new ArrayList<>();
        for(ServicePackage sp : servicePackages) {
            ServiceView sv = new ServiceView();
            sv.setId(sp.getId());
            sv.setName(sp.getName());
            sv.setPrice(sp.getPrice());
            serviceViews.add(sv);
        }
        return serviceViews;
    }

    @Override
    public boolean delete(Integer id) {
        ServicePackage servicePackage = servicePackageRepository.getOne(id);
        if (servicePackage.getOrders() == null && servicePackage.getOrders().size() == 0) {
            servicePackageRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

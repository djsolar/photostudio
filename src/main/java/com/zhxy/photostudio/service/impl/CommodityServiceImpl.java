package com.zhxy.photostudio.service.impl;

import com.zhxy.photostudio.domain.Commodity;
import com.zhxy.photostudio.repository.CommodityRepository;
import com.zhxy.photostudio.service.CommodityService;
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
import java.util.List;

@Service
@Transactional
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    private CommodityRepository commodityRepository;

    @Override
    public Commodity saveCommodity(Commodity commodity) {
        commodity.setCreateTime(System.currentTimeMillis());
        commodity.setUpdateTime(System.currentTimeMillis());
        return commodityRepository.saveAndFlush(commodity);
    }

    @Override
    public void deleteCommodity(Integer id) {
        commodityRepository.deleteById(id);
    }

    @Override
    public DataTableViewPage<Commodity> listCommodity(int page, int pageSize, String searchValue) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Page<Commodity> commodityPage;
        if (StringUtils.isEmpty(searchValue)) {
            commodityPage = commodityRepository.findAll(PageRequest.of(page, pageSize, sort));
        } else {
            Specification<Commodity> commoditySpecification = new Specification<Commodity>() {
                @Override
                public Predicate toPredicate(Root<Commodity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Predicate p1 = cb.like(root.get("name").as(String.class), "%" + searchValue + "%");
                    Predicate p2 = cb.like(root.get("type").as(String.class), "%" + searchValue + "%");
                    Predicate p3 = cb.like(root.get("price").as(String.class), "%" + searchValue + "%");
                    Predicate p4 = cb.like(root.get("unit").as(String.class), "%" + searchValue + "%");
                    return cb.or(p1, p2, p3, p4);
                }
            };
            commodityPage = commodityRepository.findAll(commoditySpecification, PageRequest.of(page, pageSize, sort));

        }
        DataTableViewPage<Commodity> dataTableViewPage = new DataTableViewPage<>();;
        dataTableViewPage.setAaData(commodityPage.getContent());
        dataTableViewPage.setRecordsTotal(commodityPage.getTotalElements());
        dataTableViewPage.setRecordsFiltered(commodityPage.getTotalElements());
        return dataTableViewPage;
    }

}

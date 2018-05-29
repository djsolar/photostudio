package com.zhxy.photostudio.controller;

import com.zhxy.photostudio.domain.Commodity;
import com.zhxy.photostudio.service.CommodityService;
import com.zhxy.photostudio.util.DataTableViewPage;
import com.zhxy.photostudio.util.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "admin")
public class AdminController {

    @Autowired
    private CommodityService commodityService;

    @RequestMapping(value = "index")
    public String index() {
        return "admin_index";
    }

    @RequestMapping(value = "home")
    public String home() {
        return "admin_home";
    }

    @RequestMapping(value = "activity")
    public String activity() {
        return "admin_activity";
    }

    @RequestMapping(value = "notice")
    public String notice() {
        return "admin_notice";
    }

    @RequestMapping(value = "order")
    public String order() {
        return "admin_order";
    }

    @RequestMapping(value = "photo")
    public String photo() {
        return "admin_photo";
    }

    @RequestMapping(value = "role")
    public String role() {
        return "admin_role";
    }

    @RequestMapping(value = "service")
    public String service() {
        return "admin_service";
    }

    @RequestMapping(value = "customer")
    public String customer() {
        return "admin_customer";
    }

    @RequestMapping(value = "account")
    public String account() {
        return "admin_account";
    }

    @RequestMapping(value = "addOrder")
    public String addOrder() {
        return "admin_add_order";
    }

    @RequestMapping(value = "checkOrder")
    public String checkOrder() {
        return "admin_check_order";
    }

    @RequestMapping(value = "commodity")
    public String commodity() {
        return "admin_commodity";
    }

    @RequestMapping(value = "commodity/add")
    @ResponseBody
    public ResponseBean<String> addCommodity(Commodity commodity) {
        commodityService.saveCommodity(commodity);
        return new ResponseBean<>(true);
    }

    @RequestMapping(value = "commodity/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean<String> deleteCommodity(Integer id) {
        commodityService.deleteCommodity(id);
        return new ResponseBean<>(true);
    }

    @RequestMapping(value = "commodity/list")
    @ResponseBody
    public DataTableViewPage<Commodity> listCommodity(HttpServletRequest request) {
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        String searchValue = request.getParameter("search[value]");
        System.out.println("searchValue: " + searchValue);
        int page = start / length;
        return commodityService.listCommodity(page, length, searchValue);
    }
}

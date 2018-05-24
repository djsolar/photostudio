package com.zhxy.photostudio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "admin")
public class AdminController {

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
}

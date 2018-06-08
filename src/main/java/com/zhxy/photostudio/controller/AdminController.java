package com.zhxy.photostudio.controller;

import com.zhxy.photostudio.domain.*;
import com.zhxy.photostudio.service.*;
import com.zhxy.photostudio.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "admin")
public class AdminController {

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ServicePackageService servicePackageService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AlbumPhotoService albumPhotoService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

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

    @RequestMapping(value = "/activity/add", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseBean<String> addActivity(Integer id, String caption, String description, String startDate, String endDate, MultipartFile activityThumbnail, MultipartFile activityPhoto) {
        activityService.save(id, caption, description, startDate, endDate, activityThumbnail, activityPhoto);
        return new ResponseBean<>(true);
    }

    @RequestMapping(value = "/activity/delete", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseBean<String> deleteActivity(Integer id) {
        activityService.delete(id);
        return new ResponseBean<>(true);
    }

    @RequestMapping(value = "/activity/list")
    @ResponseBody
    public ResponseBean<List<Activity>> listActivity() {
        List<Activity> activities = activityService.listActivity();
        return new ResponseBean<>(activities, true);
    }

    @RequestMapping(value = "/activity/eye", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseBean<ActivityView> findActivityContent(Integer id) {
        ActivityView activityView = activityService.findActivityContent(id);
        return new ResponseBean<>(activityView, true);
    }

    @RequestMapping(value = "/activity/getOne", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseBean<Activity> findActivity(Integer id) {
        Activity activity = activityService.findActivityById(id);
        return new ResponseBean<>(activity, true);
    }

    @RequestMapping(value = "notice")
    public String notice() {
        return "admin_notice";
    }

    @RequestMapping(value = "order")
    public String order() {
        return "admin_order";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "账户名或者密码错误");
        }
        if (logout != null){
            model.addAttribute("message", "账户已经退出");
        }
        return "admin_login";
    }

    @RequestMapping(value = "/loginVerify", method = {RequestMethod.POST})
    public ModelAndView login(String username, String password, HttpServletRequest request) {
        username = username.trim();
        ModelAndView modelAndView = new ModelAndView();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            modelAndView.addObject("message", "密码或用户名不能为空");
            modelAndView.setView(new RedirectView("/admin/login"));
            return modelAndView;
        }
        try {
            UsernamePasswordAuthenticationToken requestToke = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(requestToke);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            modelAndView.setView(new RedirectView("/admin/index"));
        } catch (AuthenticationException e) {
            modelAndView.addObject("message", "密码或用户名错误");
            modelAndView.setView(new RedirectView("/admin/login"));
        }

        return modelAndView;
    }


    @RequestMapping(value = "/order/add", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseBean<String> orderAdd(Order order, Integer customerId, Integer serviceId) {
        orderService.saveOrder(order, customerId, serviceId);
        return new ResponseBean<>(true);
    }

    @RequestMapping(value = "/order/delete", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseBean<String> orderDelete(Integer id) {
        orderService.delete(id);
        return new ResponseBean<>(true);
    }

    @RequestMapping(value = "/order/get", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseBean<OrderView> getOrder(Integer orderId) {
        OrderView orderView = orderService.getOrder(orderId);
        return new ResponseBean<>(orderView, true);
    }

    @RequestMapping(value = "/order/list")
    @ResponseBody
    public DataTableViewPage<OrderView> listOrder(HttpServletRequest request) {
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        String searchValue = request.getParameter("search[value]");
        System.out.println("searchValue: " + searchValue);
        int page = start / length;
        return orderService.listOrder(page, length, searchValue);
    }

    @RequestMapping(value = "photo")
    public String photo() {
        return "admin_photo";
    }
    @RequestMapping(value = "/photoAlbum/upload", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseBean<String> uploadPhoto(Integer id, String caption, String category, String description,
                                            @RequestParam("photoThumbnail") MultipartFile photoThumbnail, @RequestParam("photo") MultipartFile photo) {
        return albumPhotoService.upload(id, caption, category, description, photoThumbnail, photo);
    }

    @RequestMapping(value = "/photoAlbum/delete", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseBean<String> deletePhotoAlbum(Integer id) {
        albumPhotoService.delete(id);
        return new ResponseBean<>(true);
    }

    @RequestMapping(value = "/photoAlbum/top", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseBean<String> updatePhotoAlbumTop(Integer id, Boolean top) {
        int code = albumPhotoService.top(id, top);
        System.out.println("code: " + code);
        return new ResponseBean<>(true);
    }

    @RequestMapping(value = "/photoAlbum/list")
    @ResponseBody
    public ResponseBean<List<PhotoAlbum>> listPhoto() {
        return new ResponseBean<>(albumPhotoService.listPhotoAlbum(), true);
    }

    @RequestMapping(value = "/photoAlbum/eye", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseBean<PhotoAlbumView> eyePhotoContent(Integer id) {
        PhotoAlbumView photoAlbumView = albumPhotoService.findPhotoAlbumById(id);
        if (photoAlbumView == null) {
            return new ResponseBean<>(false);
        }
        return new ResponseBean<>(photoAlbumView, true);
    }

    @RequestMapping(value = "role")
    public String role(Model model) {

        List<Authority> authorities = authorityService.findAll();
        model.addAttribute("authorities", authorities);
        return "admin_role";
    }

    @RequestMapping(value = "/role/list")
    @ResponseBody
    public DataTableViewPage<Role> listRole(HttpServletRequest request) {
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        String searchValue = request.getParameter("search[value]");
        System.out.println("searchValue: " + searchValue);
        int page = start / length;
        return roleService.listRole(page, length, searchValue);
    }

    @RequestMapping(value = "/role/add", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseBean<String> addRole(Integer id, String name, @RequestParam(value = "authorities[]") Integer[] authorities) {
        roleService.save(id, name, authorities);
        return new ResponseBean<>(true);
    }

    @RequestMapping(value = "/role/delete", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseBean<String> deleteRole(Integer id) {
        boolean success = roleService.delete(id);
        return new ResponseBean<>(success);
    }

    @RequestMapping(value = "/role/getOne", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseBean<Role> getOne(Integer id) {
        Role role = roleService.findOne(id);
        return new ResponseBean<>(role, true);
    }

    @RequestMapping(value = "/role/all")
    @ResponseBody
    public ResponseBean<List<Role>> findAllRole() {
        return new ResponseBean<>(roleService.findAll(), true);
    }

    @RequestMapping(value = "service")
    public String service() {
        return "admin_service";
    }

    @RequestMapping(value = "/service/add", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseBean<String> addService(ServicePackage servicePackage) {
        servicePackageService.save(servicePackage);
        return new ResponseBean<>(true);
    }

    @RequestMapping(value = "/service/delete", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseBean<String> deleteService(Integer id) {
        servicePackageService.delete(id);
        return new ResponseBean<>(true);
    }

    @RequestMapping(value = "/service/list")
    @ResponseBody
    public DataTableViewPage<ServicePackage> listService(HttpServletRequest request) {
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        String searchValue = request.getParameter("search[value]");
        System.out.println("searchValue: " + searchValue);
        int page = start / length;
        return servicePackageService.listService(page, length, searchValue);
    }

    @RequestMapping(value = "/service/all")
    @ResponseBody
    public ResponseBean<List<ServiceView>> listAllService() {
        return new ResponseBean<>(servicePackageService.listAllService(), true);
    }

    @RequestMapping(value = "customer")
    public String customer() {
        return "admin_customer";
    }

    @RequestMapping(value = "/customer/add", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseBean<String> addCustomer(Customer customer) {
        customerService.save(customer);
        return new ResponseBean<>(true);
    }

    @RequestMapping(value = "/customer/delete")
    @ResponseBody
    public ResponseBean<String> deleteCustomer(Integer id) {
        customerService.delete(id);
        return new ResponseBean<>(true);
    }

    @RequestMapping(value = "/customer/list")
    @ResponseBody
    public DataTableViewPage<Customer> listCustomer(HttpServletRequest request) {
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        String searchValue = request.getParameter("search[value]");
        System.out.println("searchValue: " + searchValue);
        int page = start / length;
        return customerService.listCustomer(page, length, searchValue);
    }

    @RequestMapping(value = "account")
    public String account() {
        return "admin_account";
    }

    @RequestMapping(value = "/account/list")
    @ResponseBody
    public DataTableViewPage<User> listUser(HttpServletRequest request) {
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        String searchValue = request.getParameter("search[value]");
        System.out.println("searchValue: " + searchValue);
        int page = start / length;
        return userService.listUser(page, length, searchValue);
    }

    @RequestMapping(value = "/account/add", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseBean<String> addUser(User user, Integer roleId) {
        userService.saveUser(user, roleId);
        return new ResponseBean<>(true);
    }

    @RequestMapping(value = "/account/update", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseBean<String> updateUser(Integer id, String nickName, String password, String newPassword, String confirmPassword, MultipartFile avatar) {
        User user = userService.updateUser(id, nickName, password, newPassword, confirmPassword, avatar);
        return new ResponseBean<>(user != null);
    }

    @RequestMapping(value = "/account/delete", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseBean<String> deleteUser(Integer id) {
        boolean success = userService.deleteUser(id);
        return new ResponseBean<>(success);
    }

    @RequestMapping(value = "/account/reset", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseBean<String> resetUser(Integer id) {
        boolean success = userService.resetUser(id);
        return new ResponseBean<>(success);
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

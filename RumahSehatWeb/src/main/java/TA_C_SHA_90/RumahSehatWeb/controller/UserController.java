package TA_C_SHA_90.RumahSehatWeb.controller;

import TA_C_SHA_90.RumahSehatWeb.model.RoleModel;
import TA_C_SHA_90.RumahSehatWeb.model.UserModel;
import TA_C_SHA_90.RumahSehatWeb.service.RoleService;
import TA_C_SHA_90.RumahSehatWeb.service.UserService;
import org.apache.catalina.Role;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/add")
    private String addUserFormPage(Model model) {
        UserModel user = new UserModel();
        List<RoleModel> listRole = roleService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("listRole", listRole);
        return "form-add-user";
    }

    @PostMapping(value = "/add")
    private String addUserSubmit(@ModelAttribute UserModel user, Model model) {
        user.setIsSso(false);
        userService.addUser(user);
        model.addAttribute("user", user);
        return "redirect:/";
    }
}

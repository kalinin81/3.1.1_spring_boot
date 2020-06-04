package web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/")
public class UserController extends HttpServlet {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "login")
    public String loginGet() {
        return "login";
    }

    @GetMapping(value = "admin")
    public String usersGet(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", userService.getAllRoles());
        return "admin";
    }

    @PostMapping(value = "admin/add")
    public String addPost(User user, String[] roleIds) {
        user.setRole(userService.getRoles(roleIds));
        userService.insert(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "admin/edit")
    public String editGet(ModelMap model, @RequestParam("id") Long id) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        List<Role> roles = userService.getAllRoles();
        roles.forEach(role -> role.setInUser(user.isRoleInUser(role)));
        model.addAttribute("roles", roles);
        return "admin/edit";
    }

    @PostMapping(value = "admin/edit")
    public String editPost(User user, String[] roleIds) {
        user.setRole(userService.getRoles(roleIds));
        userService.update(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "admin/delete")
    public String deletePost(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping(value = "user")
    public String userGet(ModelMap modelMap, HttpSession httpSession) {
        modelMap.addAttribute("user", httpSession.getAttribute("user"));
        return "user";
    }

}

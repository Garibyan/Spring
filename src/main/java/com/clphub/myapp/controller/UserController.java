package com.clphub.myapp.controller;


import com.clphub.myapp.model.User;
import com.clphub.myapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public String getAllEmployees(Model model) {
        List<User> list = service.getAllUsers();

        model.addAttribute("users", list);
        return "list-users";
    }

    @RequestMapping(path = {"/edit", "/edit/{id}"})
    public String editEmployeeById(Model model, @PathVariable("id") Optional<Long> id) {
        if (id.isPresent()) {
            User user = service.getUserById(id.get());
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", new User());
        }
        return "add-edit-user";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployeeById(Model model, @PathVariable("id") Long id) {
        service.deleteUserById(id);
        return "redirect:/users";
    }

    @PostMapping("/createuser")
    public String createOrUpdateEmployee(User user) {
        service.createOrUpdateUser(user);
        return "redirect:/users";
    }



}

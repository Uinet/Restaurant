package com.github.uinet.project.controller;

import com.github.uinet.project.domain.Role;
import com.github.uinet.project.domain.User;
import com.github.uinet.project.exception.UserException;
import com.github.uinet.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationFormController {

    private final UserService userService;

    @Autowired
    public RegistrationFormController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String regPage(){
        return "registration";
    }

    @PostMapping("/registration")
    public String regUser(User user, Model model){
        try {
            user.setRole(Collections.singleton(Role.CLIENT));
            userService.registerNewUser(user);
        } catch (UserException e) {
            e.printStackTrace();
            model.addAttribute("userIsExist", true);
            return "registration";
        }
        return "redirect:/login";
    }
}

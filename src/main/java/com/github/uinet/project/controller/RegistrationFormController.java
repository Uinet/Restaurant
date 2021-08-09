package com.github.uinet.project.controller;

import com.github.uinet.project.domain.Role;
import com.github.uinet.project.domain.User;
import com.github.uinet.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Optional;

@Controller
public class RegistrationFormController {

    private final UserRepository userRepository;

    @Autowired
    public RegistrationFormController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/registration")
    public String regPage(){
        return "registration";
    }

    @Transactional
    @PostMapping("/registration")
    public String regUser(User user, Model model){
        Optional<User> userFromDB = userRepository.findByUsername(user.getUsername());
        model.addAttribute("userIsExist", userFromDB.isPresent());
        if(userFromDB.isPresent()){
            return "registration";
        }
        else{
            user.setRole(Collections.singleton(Role.CLIENT));
            userRepository.save(user);
            return "redirect:/login";
        }
    }
}

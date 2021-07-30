package com.github.uinet.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegistrationFormController {

    @RequestMapping("/reg")
    public String regPage(){
        return "registration";
    }
}

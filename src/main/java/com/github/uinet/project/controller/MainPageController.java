package com.github.uinet.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    @GetMapping("/")
    public String mainPage(){
        return "main";
    }

    @GetMapping("/about")
    public String aboutPage(){return  "about";}
}

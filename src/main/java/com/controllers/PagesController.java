package com.controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PagesController {

    @GetMapping("/")
    public String indexPage(){
        return "index";
    }

    @GetMapping("/home")
    public String homePage(){
        return "home";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/admin")
    public String adminPage(){
        return "admin";
    }
}

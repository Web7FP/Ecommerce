package com.springboot.ecommerce.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {
    @GetMapping("/home")
    public String getHomePage(){
        return "home";
    }

    @GetMapping("/login")
    public String getSignInPage(){
        return "login";
    }
}

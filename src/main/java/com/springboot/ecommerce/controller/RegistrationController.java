package com.springboot.ecommerce.controller;


import com.springboot.ecommerce.security.registration.RegistrationRequest;
import com.springboot.ecommerce.security.registration.RegistrationService;
import com.springboot.ecommerce.model.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.springboot.ecommerce.model.user.UserRole.*;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @GetMapping
    public String getSignUpPage(Model model){
        RegistrationRequest request = new RegistrationRequest();
        model.addAttribute("request", request);
        List<UserRole> roleList = Arrays.asList(USER,VENDOR, ADMIN);
        model.addAttribute("roleList", roleList);
        return  "registration";
    }

    @PostMapping("register")
    public String register(@ModelAttribute("request") RegistrationRequest request){
        registrationService.register(request);
        return "redirect:/home";
    }

    @GetMapping("confirm")
    public String confirm(@RequestParam("token") String token){
        registrationService.confirmedToken(token);
        return "redirect:/login";
    }


}

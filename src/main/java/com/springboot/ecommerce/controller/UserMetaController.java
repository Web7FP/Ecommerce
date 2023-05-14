package com.springboot.ecommerce.controller;


import com.springboot.ecommerce.model.userMeta.UserMeta;
import com.springboot.ecommerce.model.userMeta.UserMetaGender;
import com.springboot.ecommerce.model.userMeta.UserMetaServiceImpl;
import com.springboot.ecommerce.model.user.User;
import com.springboot.ecommerce.model.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.springboot.ecommerce.model.userMeta.UserMetaGender.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class UserMetaController {
    private final UserService userService;

    private final UserMetaServiceImpl userMetaService;

    @GetMapping("edit")
    public String addNewAccountInformation(Model model,
                                           @AuthenticationPrincipal UserDetails user){
        User currentUser = userService.findByEmail(user.getUsername());
        UserMeta userMeta = userMetaService.getUserMetaByCurrentUser(currentUser.getId());
        List<UserMetaGender> genderList = new ArrayList<>(Arrays.asList(MALE,FEMALE));
        model.addAttribute("userMeta", Objects.requireNonNullElseGet(userMeta, UserMeta::new));
        model.addAttribute("genderList", genderList);
        return "account-information";
    }

    @PostMapping("save-user-meta")
    public String saveUserMeta(@ModelAttribute("userMeta") UserMeta userMeta,
                               @AuthenticationPrincipal UserDetails user){
        User currentUser = userService.findByEmail(user.getUsername());
        if (userMeta.getUser() == null){
            userMeta.setUser(currentUser);
        }
        userMetaService.saveUserMeta(userMeta);
        return "redirect:/account/edit";
    }



}

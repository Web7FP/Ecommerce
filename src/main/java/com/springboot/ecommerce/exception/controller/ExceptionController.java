package com.springboot.ecommerce.exception.controller;


import com.springboot.ecommerce.exception.EmailAlreadyTakenException;
import com.springboot.ecommerce.exception.EmailNotValidException;
import com.springboot.ecommerce.exception.EmptyCartException;
import com.springboot.ecommerce.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
public class ExceptionController {


    @ExceptionHandler(EmailNotValidException.class)
    public RedirectView emailNotValidExceptionHandler(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("emailNotValidException", "email not valid");
        return new RedirectView("/registration");
    }

    @ExceptionHandler({EmailAlreadyTakenException.class})
    public RedirectView emailAlreadyTakenExceptionHandler(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("emailAlreadyTakenException","Email already taken");
        return new RedirectView("/registration");
    }

    @ExceptionHandler({EmptyCartException.class})
    public String emptyCartTakenExceptionHandler(Model model){
        model.addAttribute("emptyCartException", "Your cart is empty");
        return "cart";
    }
}

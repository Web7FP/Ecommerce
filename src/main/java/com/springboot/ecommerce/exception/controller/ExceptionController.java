package com.springboot.ecommerce.exception.controller;


import com.springboot.ecommerce.exception.EmailAlreadyTakenException;
import com.springboot.ecommerce.exception.EmailNotValidException;
import com.springboot.ecommerce.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundExceptionHandler(Model model){
        model.addAttribute("test","hello :))");
        return "notFoundException";
    }

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
}

package com.springboot.ecommerce.exception.controller;


import com.springboot.ecommerce.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

    @ExceptionHandler({QuantityExceededCartException.class})
    public ResponseEntity<?> quantityExceededExceptionHandler(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantity exceeded");
    }

    @ExceptionHandler({QuantityExceededOrderException.class})
    public RedirectView quantityExceededOrderExceptionHandler(
            RedirectAttributes redirectAttributes,
            QuantityExceededOrderException ex
    ){
        redirectAttributes.addFlashAttribute("quantityExceededOrderException", "Quantity Exceeded");
        redirectAttributes.addAttribute("orderId", ex.getOrderId());
        return new RedirectView("/order-management/order-detail/{orderId}");
    }

    @ExceptionHandler({EmptyUserMetaException.class})
    public String  emptyUserMetaExceptionHandler(){
        return "redirect:/account/edit";
    }

    @ExceptionHandler({ProductNotFoundException.class})
    public String productNotFoundException(Model model){
        model.addAttribute("productNotFoundException", "Sorry, no products found matching your selection.");
        return "result-search";
    }
}

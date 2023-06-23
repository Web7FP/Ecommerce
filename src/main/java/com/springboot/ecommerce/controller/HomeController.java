package com.springboot.ecommerce.controller;


import com.springboot.ecommerce.model.cart.Cart;
import com.springboot.ecommerce.model.cart.CartServiceImpl;
import com.springboot.ecommerce.model.category.Category;
import com.springboot.ecommerce.model.category.CategoryServiceImpl;
import com.springboot.ecommerce.model.product.Product;
import com.springboot.ecommerce.model.product.ProductServiceImpl;
import com.springboot.ecommerce.model.user.User;
import com.springboot.ecommerce.model.user.UserRole;
import com.springboot.ecommerce.model.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ProductServiceImpl productService;
    private final UserService userService;
    private final CartServiceImpl cartService;


    @GetMapping("/setCartSession")
    public String setCartSession(@AuthenticationPrincipal UserDetails user, HttpSession session){

        User currentUser = userService.findByEmail(user.getUsername());
        if (currentUser.getUserRole().equals(UserRole.ADMIN)) {
            return "redirect:/admin";
        } else {
            Cart activeCart = cartService.getActiveCartByUser(currentUser.getId());
            cartService.setActiveCartSessionAttribute(session, activeCart);
            return "redirect:/home";
        }

    }

    @GetMapping("/admin")
    public String adminPage(){
        return "admin";
    }

    @GetMapping("/home")
    public String getHomePage(Model model){
        model.addAttribute("phoneProducts",
                productService.getAllProductByCategoryName(
                        "Smart Phone",
                        productService.findPaginated(1, 10, "title", "asc")).getContent()
        );
        model.addAttribute("laptopProducts",
                productService.getAllProductByCategoryName(
                        "Laptop",
                        productService.findPaginated(1, 10, "title", "asc")).getContent()
        );
        model.addAttribute("watchProducts",
                productService.getAllProductByCategoryName(
                        "Watch",
                        productService.findPaginated(1,10, "title", "asc")).getContent());
        return "home";
    }

    @GetMapping("/login")
    public String getSignInPage(){
        return "login";
    }

    @GetMapping("/product/{slugProduct}")
    public String getProductDetailForCustomer(
            @PathVariable("slugProduct") String slugProduct,
            Model model){
        Product product = productService.findBySlugProduct(slugProduct);
        model.addAttribute("product", product);
        model.addAttribute("relatedProducts",
                productService.getAllRelatedProduct(
                        product, productService.findPaginated(1,10, "title", "asc")).getContent()
        );
        return "product-detail";
    }


}

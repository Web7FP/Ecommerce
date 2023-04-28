package com.springboot.ecommerce.controller;


import com.springboot.ecommerce.model.cart.Cart;
import com.springboot.ecommerce.model.cart.CartServiceImpl;
import com.springboot.ecommerce.model.product.Product;
import com.springboot.ecommerce.model.product.ProductServiceImpl;
import com.springboot.ecommerce.user.User;
import com.springboot.ecommerce.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class TemplateController {
    private final ProductServiceImpl productService;
    private final UserService userService;
    private final CartServiceImpl cartService;


    @GetMapping("/setCartSession")
    public String setCartSession(@AuthenticationPrincipal UserDetails user, HttpSession session){
        User currentUser = userService.findByEmail(user.getUsername());
        Cart activeCart = cartService.getActiveCartByUser(currentUser.getId());
        cartService.setActiveCartSessionAttribute(session, activeCart);
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String getHomePage(Model model){
//        User currentUser = userService.findByEmail(user.getUsername());
//        model.addAttribute("currentUser", currentUser);
        return findPaginatedProduct(1, "title", "asc",model);
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
        return "product-detail";
    }

    @GetMapping("home/product-list/page/{pageNo}")
    public String findPaginatedProduct(
            @PathVariable("pageNo") int pageNo,
            @RequestParam("sortField") String sortField,
            @RequestParam("sortDir") String sortDir,
            Model model
    ){
        int pageSize = 8;
        Page<Product> page = productService.findPaginated(pageNo, pageSize, sortField, sortDir);
        model.addAttribute("listProducts", page.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        return "home";
    }
}

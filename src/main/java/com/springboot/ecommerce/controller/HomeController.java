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
        model.addAttribute("relatedProducts",
                productService.getAllRelatedProduct(
                        product, productService.findPaginated(1,5, "title", "asc")).getContent()
        );
        return "product-detail";
    }

    @GetMapping("/home/product-list/page/{pageNo}")
    public String findPaginatedProduct(
            @PathVariable("pageNo") int pageNo,
            @RequestParam("sortField") String sortField,
            @RequestParam("sortDir") String sortDir,
            Model model
    ){
        int pageSize = 5;
        Page<Product> page = productService.getAllProducts(pageNo, pageSize, sortField, sortDir);


        model.addAttribute("listProducts", page.getContent());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
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
        return "home";
    }

    @GetMapping("/category/{slugCategory}")
    public String getProductsByCategory(@PathVariable("slugCategory") String categorySlug,
                                        @RequestParam(value = "sortField", defaultValue = "title") String sortField,
                                        @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection,
                                        @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                                        Model model){
        int pageSize = 5;
        Page<Product> productPage  = productService.getAllProductByCategorySlug(
                categorySlug, productService.findPaginated(pageNo, pageSize, sortField, sortDirection)
        );
        model.addAttribute("productsList", productPage.getContent());
        model.addAttribute("totalItems", productPage.getTotalElements());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("sortDir", sortDirection);
        model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");
        return "products-category-tag";
    }


    @GetMapping("/tag/{slugTag}")
    public String getProductsByTag(@PathVariable("slugTag") String tagSlug,
                                   @RequestParam(value = "sortField", defaultValue = "title") String sortField,
                                   @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection,
                                   @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                                   Model model
    ){
        int pageSize = 5;
        model.addAttribute("productsList",
                productService.getAllProductByTagSlug(
                        tagSlug,
                        productService.findPaginated(pageNo, pageSize, sortField, sortDirection)
                ).getContent());

        return "products-category-tag";
    }


}

package com.springboot.ecommerce.controller;

import com.springboot.ecommerce.exception.EmptyCartException;
import com.springboot.ecommerce.model.cart.Cart;
import com.springboot.ecommerce.model.cart.CartServiceImpl;
import com.springboot.ecommerce.model.cartItem.CartItem;
import com.springboot.ecommerce.model.cartItem.CartItemServiceImpl;
import com.springboot.ecommerce.model.product.Product;
import com.springboot.ecommerce.model.product.ProductServiceImpl;
import com.springboot.ecommerce.user.User;
import com.springboot.ecommerce.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final ProductServiceImpl productService;
    private final UserService userService;
    private final CartServiceImpl cartService;
    private final CartItemServiceImpl cartItemService;


    @GetMapping("")
    public String viewCartPage(Model model,
                               HttpSession session){
        Cart activeCart = cartService.getActiveCartBySession(session);
        if (activeCart != null && ! activeCart.getCartItems().isEmpty()){
            model.addAttribute("cart", activeCart);
            return "cart";
        } else {
            throw new EmptyCartException();
        }
    }

    @GetMapping("add-product-to-cart/{productId}")
    public String addProductToCart(@PathVariable("productId") Integer productId,
                                   @AuthenticationPrincipal UserDetails user,
                                   HttpSession session){
        User currentUser = userService.findByEmail(user.getUsername());
        Cart activeCart = cartService.getActiveCartBySession(session);
        Product product = productService.getProductById(productId);

        if (activeCart == null){
            activeCart = new Cart();
            activeCart.setUser(currentUser);
            currentUser.getCarts().add(activeCart);
            cartService.saveCart(activeCart);
        }

        CartItem cartItem = cartItemService.getCartItemByProductAndCart(product.getId(),
                activeCart.getId());
        if (cartItem == null){
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(activeCart);
            cartItemService.saveCartItem(cartItem);
            activeCart.getCartItems().add(cartItem);
            activeCart.setUser(currentUser);
            cartService.saveCart(activeCart);
        }
        cartService.saveCart(activeCart);
        cartService.setActiveCartSessionAttribute(session, activeCart);
        return "redirect:/cart";
    }


    @GetMapping("delete-cart-item/{cartItemId}")
    public String deleteCartItem(@PathVariable("cartItemId") Integer cartItemId,
                                 @AuthenticationPrincipal UserDetails user,
                                 HttpSession session){
        User currentUser = userService.findByEmail(user.getUsername());
        Cart activeCart = cartItemService.deleteCartItem(cartItemId, currentUser);
        cartService.setActiveCartSessionAttribute(session, activeCart);
        return "redirect:/cart";
    }




}

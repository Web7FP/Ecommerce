package com.springboot.ecommerce.controller;

import com.springboot.ecommerce.exception.EmptyCartException;
import com.springboot.ecommerce.exception.QuantityExceededException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


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

    @PostMapping("add-product-to-cart/{productId}/{quantity}")
    @ResponseBody
    public void addProductToCart(@PathVariable("productId") Integer productId,
                                 @PathVariable("quantity") Long quantity,
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

        CartItem cartItem = cartItemService.getCartItemByProductAndCart(product.getId(), activeCart.getId());
        if (cartItem == null){
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(activeCart);
            cartItem.setQuantity(quantity);
            BigDecimal priceCartItem = BigDecimal.valueOf(quantity).multiply(product.getPrice());
            cartItem.setPrice(priceCartItem);
            cartItem.setDiscount(product.getDiscount());
            cartItemService.saveCartItem(cartItem);
            activeCart.getCartItems().add(cartItem);
            activeCart.setUser(currentUser);
            cartService.updateSubTotal(activeCart);
            cartService.saveCart(activeCart);
            cartService.setActiveCartSessionAttribute(session, activeCart);
        } else {
            Long newQuantityCartItem = cartItem.getQuantity() + quantity;
            if (newQuantityCartItem <= product.getQuantity()){
                cartItem.setQuantity(newQuantityCartItem);
                cartItemService.saveCartItem(cartItem);
                cartService.updateSubTotal(cartItem.getCart());
                cartService.setActiveCartSessionAttribute(session, cartItem.getCart());
            } else {
                throw new QuantityExceededException();
            }
        }
    }


    @GetMapping("delete-cart-item/{cartItemId}")
    public String deleteCartItem(@PathVariable("cartItemId") Integer cartItemId,
                                 @AuthenticationPrincipal UserDetails user,
                                 HttpSession session){
        User currentUser = userService.findByEmail(user.getUsername());
        Cart activeCart = cartItemService.deleteCartItem(cartItemId, currentUser);
        cartService.updateSubTotal(activeCart);
        cartService.setActiveCartSessionAttribute(session, activeCart);
        return "redirect:/cart";
    }

    @PostMapping("update-quantity-cart-item/{cartItemId}/{quantity}")
    @ResponseBody
    public void  updateQuantityCartItem(@PathVariable("cartItemId") Integer cartItemId,
                                       @PathVariable("quantity") Long quantity,
                                         HttpSession session){
        CartItem cartItem = cartItemService.getCartItemById(cartItemId);
        cartItemService.updateQuantityCartItem(cartItemId, quantity);
        cartService.setActiveCartSessionAttribute(session, cartItem.getCart());
    }

}

package com.springboot.ecommerce.controller;

import com.springboot.ecommerce.exception.EmptyCartException;
import com.springboot.ecommerce.model.cart.Cart;
import com.springboot.ecommerce.model.cart.CartServiceImpl;
import com.springboot.ecommerce.model.cartItem.CartItem;
import com.springboot.ecommerce.model.cartItem.CartItemServiceImpl;
import com.springboot.ecommerce.model.product.Product;
import com.springboot.ecommerce.model.product.ProductServiceImpl;
import com.springboot.ecommerce.model.user.User;
import com.springboot.ecommerce.model.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


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
            cartService.initNewActiveCart(activeCart, currentUser);

        }

        CartItem cartItem = cartItemService.getCartItemByProductAndCart(
                product.getId(), activeCart.getId()
        );
        if (cartItem == null){
            cartItem = new CartItem();
            cartItemService.cartItemInitializer(
                    cartItem, activeCart, product,
                    quantity, currentUser, session
            );
        } else {
            cartItemService.updateExistingCartItemWhenAddProductToCart(
                    cartItem, quantity, product, session
            );
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
    public void  updateQuantityCartItem(
            @PathVariable("cartItemId") Integer cartItemId,
            @PathVariable("quantity") Long quantity,
            HttpSession session){
        cartItemService.updateQuantityCartItem(cartItemId, quantity, session);
    }

}

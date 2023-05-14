package com.springboot.ecommerce.model.cart;

import com.springboot.ecommerce.model.user.User;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface CartService {
    List<Cart> getAllCartsByUser(Long userId);

    void saveCart(Cart cart);

    Cart getActiveCartByUser(Long userId);

    Cart getCartById(Long cartId);

    void setCompletedStatusCart(Cart cart, User currentUser);

    Cart getActiveCartBySession(HttpSession session);

    void setActiveCartSessionAttribute(HttpSession session, Cart activeCart);

    void updateSubTotal(Cart cart);

    void initNewActiveCart(Cart activeCart, User currentUser);

}

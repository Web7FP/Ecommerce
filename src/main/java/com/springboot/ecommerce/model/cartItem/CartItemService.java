package com.springboot.ecommerce.model.cartItem;

import com.springboot.ecommerce.model.cart.Cart;
import com.springboot.ecommerce.model.product.Product;
import com.springboot.ecommerce.model.user.User;
import jakarta.servlet.http.HttpSession;

public interface CartItemService {
    CartItem getCartItemByProductAndCart(Integer productId, Long cartId);

    void saveCartItem (CartItem cartItem);

    Cart deleteCartItem(Integer cartItemId, User currentUser);

    CartItem getCartItemById(Integer cartItemId);

    void updateQuantityCartItem(Integer cartItemId, Long quantity, HttpSession session);

    void updateQuantityCartItem(CartItem cartItem, Long quantity);

    void deleteCartItemByProduct(Integer productId);

    void cartItemInitializer(CartItem cartItem, Cart activeCart,
                             Product product, Long quantity,
                             User currenttUser, HttpSession session);

    void updateExistingCartItemWhenAddProductToCart(CartItem existingCartItem,
                                                    Long quantity,Product product,
                                                    HttpSession session);
}

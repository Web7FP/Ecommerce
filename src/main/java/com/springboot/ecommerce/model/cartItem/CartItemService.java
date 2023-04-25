package com.springboot.ecommerce.model.cartItem;

import com.springboot.ecommerce.model.cart.Cart;
import com.springboot.ecommerce.model.product.Product;
import com.springboot.ecommerce.user.User;

public interface CartItemService {
    CartItem getCartItemByProductAndCart(Integer productId, Long cartId);

    void saveCartItem (CartItem cartItem);

    Cart deleteCartItem(Integer cartItemId, User currentUser);

    CartItem getCartItemById(Integer cartItemId);

}

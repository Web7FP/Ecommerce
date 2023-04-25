package com.springboot.ecommerce.model.cartItem;

import com.springboot.ecommerce.model.cart.Cart;
import com.springboot.ecommerce.model.cart.CartServiceImpl;
import com.springboot.ecommerce.model.product.Product;
import com.springboot.ecommerce.model.product.ProductServiceImpl;
import com.springboot.ecommerce.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService{
    private final CartItemRepository cartItemRepository;
    private final CartServiceImpl cartService;
    private final ProductServiceImpl productService;


    @Override
    public CartItem getCartItemByProductAndCart(Integer productId, Long cartId) {
        return cartItemRepository.findByProductAndCart(productId, cartId);
    }

    @Override
    public void saveCartItem(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    @Override
    public Cart deleteCartItem(Integer cartItemId, User currentUser) {
        CartItem cartItem = this.getCartItemById(cartItemId);
        Product product = cartItem.getProduct();
        Cart activeCart = cartItem.getCart();
        activeCart.getCartItems().remove(cartItem);
        activeCart.setUser(currentUser);
        product.getCartItems().remove(cartItem);
        productService.saveProduct(product);
        cartService.saveCart(activeCart);
        cartItemRepository.deleteById(cartItemId);
        return activeCart;
    }

    @Override
    public CartItem getCartItemById(Integer cartItemId) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);
        if (optionalCartItem.isPresent()){
            return optionalCartItem.get();
        } else {
            throw new IllegalStateException("Cart Item not found for id: " + cartItemId);
        }
    }
}

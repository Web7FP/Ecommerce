package com.springboot.ecommerce.model.cart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.springboot.ecommerce.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;

    @Override
    public Cart getActiveCartBySession(HttpSession session) {
        String activeCartJson = (String)  session.getAttribute("cart");
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        Cart activeCart = null;
        if (activeCartJson != null && !activeCartJson.isEmpty()){
            try {
                activeCart = mapper.readValue(activeCartJson, Cart.class);
            } catch (JsonProcessingException e){
                e.printStackTrace();
            }
        }
        return activeCart;
    }

    @Override
    public void setActiveCartSessionAttribute(HttpSession session, Cart activeCart) {
        String activeCartJson = null;
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        if (activeCart != null){
            try {
                activeCartJson = mapper.writeValueAsString(activeCart);
            } catch (JsonProcessingException e){
                e.printStackTrace();
            }
            session.setAttribute("cart", activeCartJson);
        }
    }



    @Override
    public List<Cart> getAllCartsByUser(Long userId) {
        return cartRepository.findByUser_Id(userId);
    }

    @Override
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public Cart getActiveCartByUser(Long userId) {
        return cartRepository.getActiveCartByUser(userId);
    }

    @Override
    public void setCompletedStatusCart(Cart cart, User currentUser) {
        cart.setUser(currentUser);
        cart.setCartStatus(CartStatus.COMPLETED);
        cartRepository.save(cart);
    }

    @Override
    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId).orElse(null);
    }
}

package com.springboot.ecommerce.model.cartItem;

import com.springboot.ecommerce.model.cart.Cart;
import com.springboot.ecommerce.model.product.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    @Query("select ct " +
            "from CartItem as ct " +
            "inner join Cart as c on c.id = ct.cart.id " +
            "inner join Product as p on p.id = ct.product.id " +
            "where c.id = :cartId and p.id = :productId"
    )
    CartItem findByProductAndCart(Integer productId, Long cartId);

}

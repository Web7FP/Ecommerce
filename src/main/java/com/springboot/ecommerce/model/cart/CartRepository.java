package com.springboot.ecommerce.model.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUser_Id (Long id);

    @Query("select c " +
            "from Cart as c " +
            "where c.user.id=?1 and c.cartStatus= 'ACTIVE'")
    Cart getActiveCartByUser(Long userId);




}

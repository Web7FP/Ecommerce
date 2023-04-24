package com.springboot.ecommerce.model.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    @Query("select o " +
            "from Order as o " +
            "where o.user.id=?1")
    List<Order> getAllOrderByCurrentUser(Long currentUserId);


    @Query("select o " +
            "from Order as o " +
            "where o.user.id=?1 and o.status='COMPLETED'")
    List<Order> getCompletedOrderByCurrentUser(Long currentUserId);

    @Query("select o " +
            "from Order as o " +
            "where o.user.id=?1 and o.status='PROCESSING'")
    List<Order> getProcessingOrderByCurrentUser(Long currentUserId);

    @Query("select o " +
            "from Order as o " +
            "where o.user.id=?1 and o.status='DELIVERED'")
    List<Order> getDeliveredOrderByCurrentUser(Long currentUserId);

    @Query("select o " +
            "from Order as o " +
            "where o.user.id=?1 and o.status='CANCELLED'")
    List<Order> getCancelledOrderByCurrentUser(Long currentUserId);


    @Query("select o " +
            "from Order as o " +
            "where o.status='COMPLETED'")
    List<Order> getAllCompletedOrder();


    @Query("select o " +
            "from Order as o " +
            "where o.status='PROCESSING'")
    List<Order> getAllProcessingOrder();

    @Query("select o " +
            "from Order as o " +
            "where o.status='DELIVERED'")
    List<Order> getAllDeliveredOrder();

    @Query("select o " +
            "from Order as o " +
            "where o.status='CANCELLED'")
    List<Order> getAllCancelledOrder();
}

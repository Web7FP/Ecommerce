package com.springboot.ecommerce.model.order;

import com.springboot.ecommerce.model.orderItem.OrderItem;

import java.util.List;

public interface OrderService {

    void saveOrder(Order order, List<OrderItem> orderItems);

    void saveOrder(Order order);

    List<Order> getAllCancelledOrder();

    List<Order> getAllDeliveredOrder();

    List<Order> getAllProcessingOrder();

    List<Order> getAllCompletedOrder();

    List<Order> getAllOrder();

    List<Order> getCancelledOrderByCurrentUser(Long currentUserId);

    List<Order> getDeliveredOrderByCurrentUser(Long currentUserId);

    List<Order> getProcessingOrderByCurrentUser(Long currentUserId);

    List<Order> getCompletedOrderByCurrentUser(Long currentUserId);

    List<Order> getAllOrderByCurrentUser(Long currentUserId);

}

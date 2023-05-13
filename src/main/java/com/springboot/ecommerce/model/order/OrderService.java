package com.springboot.ecommerce.model.order;

import com.springboot.ecommerce.model.cart.Cart;
import com.springboot.ecommerce.model.orderItem.OrderItem;
import com.springboot.ecommerce.model.transaction.Transaction;
import com.springboot.ecommerce.model.userMeta.UserMeta;
import com.springboot.ecommerce.user.User;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface OrderService {

    void saveOrder(Order order,
                   List<OrderItem> orderItems,
                   UserMeta userMeta,
                   Transaction transaction);

    void saveOrder(Order order);

    void saveOrder(Order order, Transaction transaction);

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

    void setCompletedOrder(Long orderId);

    void setCancelledOrder(Long orderId);

    void setDeliveredOrder(Long Id);

    Order getOrderById(Long orderId);

    int checkCartBeforeOrder(User currentUser, HttpSession session);

    void buyAgainHandler(Long orderId, HttpSession session);

    void processingNewOrder(Transaction newTransaction, User currentUser, HttpSession session);

}

package com.springboot.ecommerce.model.order;

import com.springboot.ecommerce.model.orderItem.OrderItem;
import com.springboot.ecommerce.model.transaction.Transaction;
import com.springboot.ecommerce.model.transaction.TransactionServiceImpl;
import com.springboot.ecommerce.model.userMeta.UserMeta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final TransactionServiceImpl transactionService;



    @Override
    public void saveOrder(Order order,
                          List<OrderItem> orderItems,
                          UserMeta userMeta,
                          Transaction transaction) {
        order.setAddress(userMeta.getAddress());
        order.setMobile(userMeta.getMobile());
        order.setFirstName(userMeta.getFirstName());
        order.setMiddleName(userMeta.getMiddleName());
        order.setLastName(userMeta.getLastName());
        order.setTransaction(transaction);
        order.setOrderItems(orderItems);
        orderRepository.save(order);
    }

    @Override
    public void saveOrder(Order order, Transaction transaction) {
        order.setTransaction(transaction);
        orderRepository.save(order);
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public List<Order> getAllCancelledOrder() {
        return orderRepository.getAllCancelledOrder();
    }

    @Override
    public List<Order> getAllDeliveredOrder() {
        return orderRepository.getAllDeliveredOrder();
    }

    @Override
    public List<Order> getAllProcessingOrder() {
        return orderRepository.getAllProcessingOrder();
    }

    @Override
    public List<Order> getAllCompletedOrder() {
        return orderRepository.getAllCompletedOrder();
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getCancelledOrderByCurrentUser(Long currentUserId) {
        return orderRepository.getCancelledOrderByCurrentUser(currentUserId);
    }

    @Override
    public List<Order> getDeliveredOrderByCurrentUser(Long currentUserId) {
        return orderRepository.getDeliveredOrderByCurrentUser(currentUserId);
    }

    @Override
    public List<Order> getProcessingOrderByCurrentUser(Long currentUserId) {
        return orderRepository.getProcessingOrderByCurrentUser(currentUserId);
    }

    @Override
    public List<Order> getCompletedOrderByCurrentUser(Long currentUserId) {
        return orderRepository.getCompletedOrderByCurrentUser(currentUserId);
    }

    @Override
    public List<Order> getAllOrderByCurrentUser(Long currentUserId) {
        return orderRepository.getAllOrderByCurrentUser(currentUserId);
    }


    @Override
    public void setCompletedOrder(Order order) {
        Transaction transaction = order.getTransaction();
        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
        transactionService.setSuccessTransaction(transaction);
    }

    @Override
    public void setCancelledOrder(Order order) {
        Transaction transaction = order.getTransaction();
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        transactionService.setCancelledTransaction(transaction);
    }

    @Override
    public void setDeliveredOrder(Order order) {
        order.setStatus(OrderStatus.DELIVERED);
        orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()){
            return optionalOrder.get();
        } else {
            throw new IllegalStateException("Order not found for id: " + orderId);
        }
    }
}

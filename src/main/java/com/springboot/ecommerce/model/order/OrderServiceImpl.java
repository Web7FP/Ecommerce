package com.springboot.ecommerce.model.order;

import com.springboot.ecommerce.model.orderItem.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;


    @Override
    public void saveOrder(Order order, List<OrderItem> orderItems) {
        order.setOrderItems(orderItems);
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
}

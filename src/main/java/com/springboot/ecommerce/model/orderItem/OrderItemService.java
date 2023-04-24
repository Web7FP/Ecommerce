package com.springboot.ecommerce.model.orderItem;


import com.springboot.ecommerce.model.cartItem.CartItem;
import com.springboot.ecommerce.model.order.Order;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> setOrderItemByCartItem(List<CartItem> cartItems, Order order);

    void saveOrderItem(OrderItem orderItem);
}

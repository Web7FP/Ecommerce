package com.springboot.ecommerce.controller;


import com.springboot.ecommerce.model.cart.Cart;
import com.springboot.ecommerce.model.cart.CartServiceImpl;
import com.springboot.ecommerce.model.order.Order;
import com.springboot.ecommerce.model.order.OrderServiceImpl;
import com.springboot.ecommerce.model.orderItem.OrderItem;
import com.springboot.ecommerce.model.orderItem.OrderItemServiceImpl;
import com.springboot.ecommerce.user.User;
import com.springboot.ecommerce.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class OrderController {

    private final CartServiceImpl cartService;
    private final OrderItemServiceImpl orderItemService;
    private final UserService userService;
    private final OrderServiceImpl orderService;



    @GetMapping("/order/checkout/payment")
    public String orderProductsList(HttpSession session,
                                    @AuthenticationPrincipal UserDetails user){
        User currentUser = userService.findByEmail(user.getUsername());
        Cart cart = cartService.getActiveCartBySession(session);
        cartService.setCompletedStatusCart(cart, currentUser);
        Order order = new Order();
        order.setUser(currentUser);
        orderService.saveOrder(order);
        List<OrderItem> orderItems = orderItemService.setOrderItemByCartItem(cart.getCartItems(), order);
        orderService.saveOrder(order, orderItems);
        session.removeAttribute("cart");
        return "redirect:/home";
    }


    @GetMapping("/order-management")
    public String getAllOrder(Model model){
        model.addAttribute("ordersList", orderService.getAllOrder());
        return "management-order";
    }

    @GetMapping("/order/history")
    public String getAllOrderByCurrentUser(Model model,
                                           @AuthenticationPrincipal UserDetails user){
        User currentUser = userService.findByEmail(user.getUsername());
        model.addAttribute("processingOrderList",
                orderService.getProcessingOrderByCurrentUser(currentUser.getId()));
        model.addAttribute("deliveredOrderList",
                orderService.getDeliveredOrderByCurrentUser(currentUser.getId()));
        model.addAttribute("completedOrderList",
                orderService.getCompletedOrderByCurrentUser(currentUser.getId()));
        model.addAttribute("cancelledOrderList",
                orderService.getCancelledOrderByCurrentUser(currentUser.getId()));
        model.addAttribute("allOrderList",
                orderService.getAllOrderByCurrentUser(currentUser.getId()));
        return "order-history";
    }
}

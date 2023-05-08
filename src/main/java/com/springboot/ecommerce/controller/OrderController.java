package com.springboot.ecommerce.controller;


import com.springboot.ecommerce.model.cart.Cart;
import com.springboot.ecommerce.model.cart.CartServiceImpl;
import com.springboot.ecommerce.model.cartItem.CartItem;
import com.springboot.ecommerce.model.cartItem.CartItemServiceImpl;
import com.springboot.ecommerce.model.order.Order;
import com.springboot.ecommerce.model.order.OrderServiceImpl;
import com.springboot.ecommerce.model.orderItem.OrderItem;
import com.springboot.ecommerce.model.orderItem.OrderItemServiceImpl;
import com.springboot.ecommerce.model.transaction.Transaction;
import com.springboot.ecommerce.model.transaction.TransactionMode;
import com.springboot.ecommerce.model.transaction.TransactionServiceImpl;
import com.springboot.ecommerce.model.transaction.TransactionType;
import com.springboot.ecommerce.model.userMeta.UserMeta;
import com.springboot.ecommerce.model.userMeta.UserMetaServiceImpl;
import com.springboot.ecommerce.user.User;
import com.springboot.ecommerce.user.UserRole;
import com.springboot.ecommerce.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.springboot.ecommerce.model.transaction.TransactionMode.*;
import static com.springboot.ecommerce.model.transaction.TransactionType.*;


@Controller
@RequiredArgsConstructor
public class OrderController {

    private final CartServiceImpl cartService;
    private final OrderItemServiceImpl orderItemService;
    private final UserService userService;
    private final OrderServiceImpl orderService;
    private final UserMetaServiceImpl userMetaService;
    private final TransactionServiceImpl transactionService;
    private final CartItemServiceImpl cartItemService;



    @GetMapping("/order/checkout/payment")
    public String orderProductsList(HttpSession session,
                                    @AuthenticationPrincipal UserDetails user,
                                    Model model){
        User currentUser = userService.findByEmail(user.getUsername());
        Cart activeCart = cartService.getActiveCartBySession(session);
        List<TransactionMode> transactionModes = Arrays.asList(CASH_ON_DELIVERY, CHEQUE, WIRED, DRAFT);
        List<TransactionType> transactionTypes = Arrays.asList(DEBIT, CREDIT);
        model.addAttribute("cart",activeCart);
        model.addAttribute("userMeta",userMetaService.getUserMetaByCurrentUser(currentUser.getId()));
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("transactionTypes", transactionTypes);
        model.addAttribute("transactionModes", transactionModes);
        return "order-payment";
    }

    @PostMapping("/order/processing")
    public String processingOrder(
            HttpSession session,
            @ModelAttribute("transaction") Transaction transaction,
            @AuthenticationPrincipal UserDetails user){
            User currentUser = userService.findByEmail(user.getUsername());
            Cart activeCart = cartService.getActiveCartBySession(session);
            UserMeta userMeta = userMetaService.getUserMetaByCurrentUser(currentUser.getId());
            Order processingOrder = new Order();
            processingOrder.setUser(currentUser);
            orderService.saveOrder(processingOrder);
            transaction.setOrder(processingOrder);
            transaction.setUser(currentUser);
            transactionService.saveTransaction(transaction);
            List<OrderItem> orderItems = orderItemService.setOrderItemByCartItem(
                    activeCart.getCartItems(), processingOrder);
            orderService.saveOrder(processingOrder, orderItems, userMeta, transaction);
            cartService.setCompletedStatusCart(activeCart, currentUser);
            session.removeAttribute("cart");
        return "redirect:/order/history";
    }

    @GetMapping("/order/buy-again/{orderId}")
    public String buyAgainOrder(@PathVariable("orderId") Long orderId,
                                HttpSession session){
        Order order = orderService.getOrderById(orderId);
        Cart activeCart = cartService.getActiveCartBySession(session);
        if (activeCart == null) {
            activeCart = new Cart();
            activeCart.setUser(order.getUser());
            cartService.saveCart(activeCart);
        }
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem: orderItems) {
            CartItem existingCartItem = cartItemService
                    .getCartItemByProductAndCart(
                            orderItem.getProduct().getId(),
                            activeCart.getId()
                    );
            if (existingCartItem != null){
                Long newQuantityCartItem = existingCartItem.getQuantity() + orderItem.getQuantity();
                cartItemService.updateQuantityCartItem(
                        existingCartItem,
                        newQuantityCartItem
                );
                activeCart = existingCartItem.getCart();
                cartService.saveCart(activeCart);
            } else {
                CartItem newCartItem = new CartItem();
                newCartItem.setProduct(orderItem.getProduct());
                newCartItem.setPrice(orderItem.getPrice());
                newCartItem.setDiscount(orderItem.getDiscount());
                newCartItem.setQuantity(orderItem.getQuantity());
                newCartItem.setCart(activeCart);
                activeCart.getCartItems().add(newCartItem);
                cartItemService.saveCartItem(newCartItem);
                cartService.saveCart(activeCart);
            }

        }
        cartService.setActiveCartSessionAttribute(session, activeCart);
        return "redirect:/cart";
    }


    @GetMapping("/order-management")
    public String getAllOrder(Model model){
        model.addAttribute("allOrderList", orderService.getAllOrder());
        return "management-order";
    }

    @GetMapping("/order-management/order-detail/{orderId}")
    public String getOrderDetail(
            @PathVariable("orderId") Long orderId,
            Model model
    ){
        Order order = orderService.getOrderById(orderId);
        model.addAttribute("transaction", order.getTransaction());
        model.addAttribute("order", order);
        return "order-detail";
    }


    @GetMapping("/order-management/update-delivered-order/{orderId}")
    public String updateDeliveredOrder(@PathVariable("orderId") Long orderId,
                                       RedirectAttributes redirectAttributes){
        Order order = orderService.getOrderById(orderId);
        orderService.setDeliveredOrder(order);
        redirectAttributes.addAttribute("orderId", orderId);
        return "redirect:/order-management/order-detail/{orderId}";
    }

    @GetMapping("/order/update-cancelled-order/{orderId}")
    public String updateCancelledOrder(@PathVariable("orderId") Long orderId,
                                        RedirectAttributes redirectAttributes,
                                       @AuthenticationPrincipal UserDetails user) {
        User currentUser = userService.findByEmail(user.getUsername());
        Order order = orderService.getOrderById(orderId);
        orderService.setCancelledOrder(order);
        if (currentUser.getUserRole().equals(UserRole.ADMIN)){
            redirectAttributes.addAttribute("orderId", orderId);
            return "redirect:/order-management/order-detail/{orderId}";
        } else {
            return "redirect:/order/history";
        }

    }

    @GetMapping("/order-management/update-completed-order/{orderId}")
    public String updateCompletedOrder(@PathVariable("orderId") Long orderId,
                                       RedirectAttributes redirectAttributes){
        Order order = orderService.getOrderById(orderId);
        orderService.setCompletedOrder(order);
        redirectAttributes.addAttribute("orderId", orderId);
        return "redirect:/order-management/order-detail/{orderId}";
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

package com.springboot.ecommerce.model.order;

import lombok.Getter;

@Getter
public enum OrderStatus {
    COMPLETED,
    PROCESSING,
    DELIVERED,
    CANCELLED,
}

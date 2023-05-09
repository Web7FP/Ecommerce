package com.springboot.ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuantityExceededOrderException extends RuntimeException{
    private Long orderId;
    public QuantityExceededOrderException(String message) {
        super(message);
    }
}

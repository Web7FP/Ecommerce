package com.springboot.ecommerce.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class QuantityExceededCartException extends RuntimeException{

    public QuantityExceededCartException(String message) {
        super(message);
    }
}

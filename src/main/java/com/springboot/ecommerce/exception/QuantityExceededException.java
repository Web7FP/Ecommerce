package com.springboot.ecommerce.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class QuantityExceededException extends RuntimeException{

    public QuantityExceededException(String message) {
        super(message);
    }
}

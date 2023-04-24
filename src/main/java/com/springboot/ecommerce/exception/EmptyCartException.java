package com.springboot.ecommerce.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmptyCartException extends RuntimeException{
    public EmptyCartException(String message) {
        super(message);
    }
}

package com.springboot.ecommerce.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmptyUserMetaException extends RuntimeException{
    public EmptyUserMetaException(String message) {
        super(message);
    }
}

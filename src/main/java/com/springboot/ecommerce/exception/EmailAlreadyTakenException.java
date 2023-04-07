package com.springboot.ecommerce.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmailAlreadyTakenException extends RuntimeException{
    public EmailAlreadyTakenException(String message) {
        super(message);
    }
}

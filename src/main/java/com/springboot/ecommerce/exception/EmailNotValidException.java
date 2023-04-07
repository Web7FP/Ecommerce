package com.springboot.ecommerce.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmailNotValidException extends RuntimeException{
    public EmailNotValidException(String message) {
        super(message);
    }
}

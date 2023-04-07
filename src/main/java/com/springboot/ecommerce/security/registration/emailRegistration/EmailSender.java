package com.springboot.ecommerce.security.registration.emailRegistration;

public interface EmailSender {
    void send(String to, String email);
}

package com.springboot.ecommerce.user;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserPermission {
    PERMISSION1("user:write"),
    PERMISSION2("user:read");

    private final String permission;
}

package com.springboot.ecommerce.user;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.springboot.ecommerce.user.UserPermission.PERMISSION1;
import static com.springboot.ecommerce.user.UserPermission.PERMISSION2;


@Getter
@AllArgsConstructor
public enum UserRole {
    ADMIN(Sets.newHashSet(PERMISSION1,PERMISSION2)),
    USER(Sets.newHashSet()),
    VENDOR(Sets.newHashSet(PERMISSION2));

    private final Set<UserPermission> permissions;


    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(userPermission -> new SimpleGrantedAuthority(userPermission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}

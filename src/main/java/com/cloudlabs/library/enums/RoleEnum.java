package com.cloudlabs.library.enums;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
public enum RoleEnum {
    ADMIN(new SimpleGrantedAuthority("ADMIN")),
    USER(new SimpleGrantedAuthority("USER")),
    DISABLED(new SimpleGrantedAuthority("DISABLED"));

    private final SimpleGrantedAuthority authority;

    RoleEnum(SimpleGrantedAuthority authority) {
        this.authority = authority;
    }
}

package ru.opensolutions.fortune.model.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;


/**
 * Привилегии пользователя. */
public class Privilege implements GrantedAuthority {

    @Getter
    private String name = "admin";

    @Override
    public String getAuthority() {
        return this.name;
    }
}

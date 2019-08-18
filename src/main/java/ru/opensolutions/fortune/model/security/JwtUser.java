package ru.opensolutions.fortune.model.security;

import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * pojo объект для пользователя с параметрами авторизации. */
public class JwtUser implements UserDetails {

    /**
     * Логин пользователя. */
    private String username;
    /**
     * Права пользователя. */
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * @param username {@link JwtUser#username}.
     * @param authorities {@link JwtUser#authorities}.
     */
    public JwtUser(
            @NonNull final String username,
            @NonNull final  Collection<? extends GrantedAuthority> authorities)
    {
        this.username = username;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

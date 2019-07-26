package ru.opensolutions.fortune.model.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

import static java.util.Collections.EMPTY_LIST;

/**
 * created by vlad on 21.07.19
 */
public class JwtUser implements UserDetails {
    private String username;

    private Collection<? extends GrantedAuthority> authorities;

    private Date creationDate;

    public JwtUser(String username, Date creationDate) {
        this(username, creationDate, EMPTY_LIST);
    }

    public JwtUser(String username, Date creationDate, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.creationDate = creationDate;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
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

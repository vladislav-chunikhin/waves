package ru.opensolutions.fortune.model.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.opensolutions.fortune.model.constants.SecurityConstants;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Имплементация {@link UserDetails} для компонентов Spring Security. */
@Data
public class UserSecurity implements UserDetails {

    private String login = SecurityConstants.DEFAULT_LOGIN;

    private String password = SecurityConstants.DEFAULT_PASSWORD;

    private Boolean enabled = Boolean.TRUE;

    private List<Privilege> privileges = Collections.singletonList(new Privilege());

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.privileges;
    }

    @Override
    public String getUsername() {
        return this.login;
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
        return this.enabled;
    }
}

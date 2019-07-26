package ru.opensolutions.fortune.model.auth;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * created by vlad on 21.07.19
 */
@Data
public class UserSecurity implements UserDetails {

    private String login = "open-solutions";

    private String password = "$2a$11$C4CfCC2tED2TO/hh.RYgdu0n8R7McommC99vNH3S5EtExnjIeY3Wq";

    private Boolean enabled = Boolean.TRUE;

    private List<Privilege> privileges = Collections.singletonList(new Privilege());

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getPrivileges();
    }

    @Override
    public String getUsername() {
        return getLogin();
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
        return getEnabled();
    }
}

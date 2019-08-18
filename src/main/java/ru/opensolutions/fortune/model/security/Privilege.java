package ru.opensolutions.fortune.model.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import ru.opensolutions.fortune.model.constants.SecurityConstants;

/**
 * Привилегии пользователя. */
public class Privilege implements GrantedAuthority {

    /**
     * Наименование роли. */
    @Getter
    private String name = SecurityConstants.ADMIN_ROLE;

    @Override
    public String getAuthority() {
        return this.name;
    }
}

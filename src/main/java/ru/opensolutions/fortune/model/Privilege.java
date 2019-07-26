package ru.opensolutions.fortune.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;


/**
 * created by vlad on 21.07.19
 */
@Data
public class Privilege implements GrantedAuthority {

    private String name = "admin";

    @Override
    public String getAuthority() {
        return name;
    }
}

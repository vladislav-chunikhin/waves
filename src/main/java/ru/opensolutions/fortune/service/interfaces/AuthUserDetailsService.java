package ru.opensolutions.fortune.service.interfaces;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Сервис для поиска пользователя по логину в системе. */
public interface AuthUserDetailsService extends UserDetailsService {

    /**
     * @param login логин пользователя.
     * @return {@link UserDetails}. */
    UserDetails loadUserByUsername(String login);
}

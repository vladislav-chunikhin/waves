package ru.opensolutions.fortune.service.spring.security;

import lombok.NonNull;
import ru.opensolutions.fortune.common.AbstractLogger;
import ru.opensolutions.fortune.model.UserSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Сервис для поулчения пользователя аутентификации.
 */
@Service
public class AuthUserDetailsService extends AbstractLogger implements UserDetailsService {

    private static UserSecurity user = new UserSecurity();

    @Override
    public UserDetails loadUserByUsername(@NonNull final String login) throws UsernameNotFoundException {
        log("Попытка получения пользователя с логином = {}", login);
        if (!user.getLogin().equals(login)) {
           throw new UsernameNotFoundException(login);
        }
        return user;
    }
}

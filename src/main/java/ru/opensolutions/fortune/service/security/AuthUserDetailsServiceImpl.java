package ru.opensolutions.fortune.service.security;

import lombok.NonNull;
import ru.opensolutions.fortune.service.interfaces.AuthUserDetailsService;
import ru.opensolutions.fortune.util.log.AbstractLogger;
import ru.opensolutions.fortune.model.security.UserSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Имплементация {@link AuthUserDetailsService}. */
@Service
public class AuthUserDetailsServiceImpl extends AbstractLogger implements AuthUserDetailsService {

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

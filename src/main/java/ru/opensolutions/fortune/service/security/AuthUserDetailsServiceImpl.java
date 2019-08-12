package ru.opensolutions.fortune.service.security;

import lombok.NonNull;
import ru.opensolutions.fortune.util.log.AbstractLogger;
import ru.opensolutions.fortune.model.security.UserSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Имплементация {@link AuthUserDetailsService}. */
@Service
public class AuthUserDetailsServiceImpl extends AbstractLogger implements AuthUserDetailsService {

    /**
     * Статичный пользователь. */
    private static UserSecurity user = new UserSecurity();

    /**
     * @param login логин пользователя.
     * @return объект {@link UserDetails}, содержащий информацию о пользователе.
     * @throws UsernameNotFoundException исключение, возникающее, если не был найден пользователь по логину. */
    @Override
    public UserDetails loadUserByUsername(@NonNull final String login) throws UsernameNotFoundException {
        this.log("Попытка получения пользователя с логином = {}", login);
        if (!user.getLogin().equals(login)) {
           throw new UsernameNotFoundException(login);
        }
        return user;
    }
}

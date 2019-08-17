package ru.opensolutions.fortune.configuration.security;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import ru.opensolutions.fortune.configuration.web.filter.JwtTokenAuthenticationFilter;
import ru.opensolutions.fortune.model.constants.SecurityConstants;
import ru.opensolutions.fortune.service.security.AuthUserDetailsService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.HEAD;
import static org.springframework.http.HttpMethod.PATCH;

/**
 * Настройка для http security. */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Lazy
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Настройка по включению | выключению авторизации в проекте. */
    @Value("${auth.switch}")
    private String authSwitcher;

    /**
     * Сервис по получению авторизованного пользователя. */
    private AuthUserDetailsService authUserDetailsService;
    /**
     * Настройка для пароля. */
    private PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(final AuthUserDetailsService authUserDetailsService, final PasswordEncoder passwordEncoder) {
        super(true);
        this.authUserDetailsService = authUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Настройка для провайдера аутентификации.
     * @return Объект security провайдера.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(authUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        provider.setAuthoritiesMapper(authoritiesMapper());
        return provider;
    }

    /**
     * Настройка маппера для ролей пользователя.
     * @return Объект с настройками маппера.
     */
    @Bean
    public GrantedAuthoritiesMapper authoritiesMapper() {
        final SimpleAuthorityMapper mapper = new SimpleAuthorityMapper();
        mapper.setConvertToUpperCase(true);
        return mapper;
    }

    /**
     * @return дефолтный бин {@link AuthenticationManager}
     * @throws Exception Любые исключения при настройки. */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Устанавливаем наш настройенный провайдер в билдер управления аутентификации.
     * @param auth Билдер менеджера аутентификации.
     */
    @Override
    public void configure(@NonNull final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * Главная настройка по http фильтрации.
     * @param http http security Объект.
     * @throws Exception Любые исключения при настройки. */
    @Override
    protected void configure(@NonNull final HttpSecurity http) throws Exception {

        final String secret
                = IOUtils.toString(this.getClass().getResourceAsStream(SecurityConstants.SECRET_FILE));

        http
                .csrf().disable()
                .addFilterAfter(jwtTokenAuthenticationFilter(secret), ExceptionTranslationFilter.class)
                .addFilterAfter(corsFilter(), ExceptionTranslationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new SecurityAuthenticationEntryPoint())
                .accessDeniedHandler(new RestAccessDeniedHandler())
                .and()
                .headers()
                .cacheControl()
                .disable()
                .and()
                .anonymous()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(HttpMethod.GET, SecurityConstants.NONE_SECURITY).permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.AUTH_URL).permitAll()
                .anyRequest().authenticated();

    }

    /**
     * @return Настроенный CORS-фильтр.
     */
    private CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod(GET);
        config.addAllowedMethod(HEAD);
        config.addAllowedMethod(PUT);
        config.addAllowedMethod(POST);
        config.addAllowedMethod(OPTIONS);
        config.addAllowedMethod(DELETE);
        config.addAllowedMethod(PATCH);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    /**
     * @param secret секретный ключ в виде строки.
     * @return настроенный jwt фильтр. */
    private JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter(final String secret) {
        return new JwtTokenAuthenticationFilter(secret, authSwitcher);
    }
}

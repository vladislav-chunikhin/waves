package ru.opensolutions.fortune.configuration.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import ru.opensolutions.fortune.configuration.web.filter.JwtTokenAuthenticationFilter;
import ru.opensolutions.fortune.service.interfaces.AuthUserDetailsService;
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
import static org.springframework.http.HttpMethod.*;

/**
 * Настройка для http security. */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Lazy
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${auth.switch}")
    private String authSwitcher;

    /**
     * Сервис по получению авторизованного пользователя. */
    private AuthUserDetailsService authUserDetailsService;
    /**
     * Настройка для пароля. */
    private PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(AuthUserDetailsService authUserDetailsService, PasswordEncoder passwordEncoder) {
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
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
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
        SimpleAuthorityMapper mapper = new SimpleAuthorityMapper();
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
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * Главная настройка по http фильтрации.
     * @param http http security Объект.
     * @throws Exception Любые исключения при настройки. */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String secret = IOUtils.toString(this.getClass().getResourceAsStream(SecurityParamsConfig.SECRET_FILE));

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
                .antMatchers(HttpMethod.GET, SecurityParamsConfig.NONE_SECURITY).permitAll()
                .antMatchers(HttpMethod.POST, SecurityParamsConfig.AUTH_URL).permitAll()
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
    private JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter(String secret) {
        return new JwtTokenAuthenticationFilter(secret, authSwitcher);
    }
}
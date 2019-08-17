package ru.opensolutions.fortune.util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.util.DateUtils;
import lombok.NonNull;
import ru.opensolutions.fortune.model.constants.SecurityConstants;
import ru.opensolutions.fortune.exception.JwtBadSignatureException;
import ru.opensolutions.fortune.exception.JwtExpirationException;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import static com.nimbusds.jose.JWSAlgorithm.HS256;

/**
 * Утильный класс для работы с jwt токеном. */
public final class JwtUtils {

    /**
     * Запрещаем создавать эксземпляр класса. */
    private JwtUtils() {
        throw new RuntimeException();
    }

    /**
     * Метод по генерации токена.
     * @param username логин пользователя.
     * @param roles роли пользователя.
     * @param secret секретный ключ.
     * @param expirationInMinutes время жизни токена в минутах.
     * @return auth токен.
     * @throws JOSEException исключение по время работы метода.
     */
    public static String generateHMACToken(
            @NonNull final String username,
            @NonNull final Collection<? extends GrantedAuthority> roles,
            @NonNull final String secret,
            @NonNull final int expirationInMinutes)
            throws JOSEException
    {
        return generateHMACToken(username, AuthorityListToCommaSeparatedString(roles), secret, expirationInMinutes);
    }

    /**
     * Метод по генерации токена.
     * @param username логин пользователя.
     * @param roles роли пользователя.
     * @param secret секретный ключ.
     * @param expirationInMinutes время жизни токена в минутах.
     * @return auth токен.
     * @throws JOSEException исключение по время работы метода.
     */
    public static String generateHMACToken(
            @NonNull final String username,
            @NonNull final String roles,
            @NonNull final String secret,
            @NonNull final int expirationInMinutes)
            throws JOSEException
    {
        final JWSSigner signer = new MACSigner(secret);
        final JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issueTime(currentDate())
                .expirationTime(expirationDate(expirationInMinutes))
                .claim(SecurityConstants.ROLES_CLAIM, roles)
                .claim(SecurityConstants.LOGIN_CLAIM, username)
                .audience(SecurityConstants.AUDIENCE_WEB)
                .build();

        final SignedJWT signedJWT =
                new SignedJWT(new JWSHeader(HS256), claimsSet);
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }

    /**
     * Получение текущей даты.
     * @return Дата.
     */
    private static Date currentDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * Проверка на свежесть токена по времени.
     * @param jwt токен.
     * @throws ParseException исключение во время парсинга токена.
     */
    public static void assertNotExpired(@NonNull final SignedJWT jwt) throws ParseException
    {
        if (Objects.nonNull(jwt.getJWTClaimsSet().getExpirationTime()) &&
                DateUtils.isBefore(jwt.getJWTClaimsSet().getExpirationTime(), currentDate(), SecurityConstants.SKEW)) {
            throw new JwtExpirationException(MessageUtils.getMessage("jwt.expiration.exception"));
        }
    }

    /**
     * Валидация структуры токена.
     * @param jwt токен.
     * @param secret секретный ключ.
     * @throws JOSEException исключение по время работы метода.
     */
    public static void assertValidSignature(
            @NonNull final SignedJWT jwt,
            @NonNull final String secret) throws JOSEException
    {
        if (!verifyHMACToken(jwt, secret)) {
            throw new JwtBadSignatureException("Сигнатура токена не действительна.");
        }
    }

    /**
     * Парсинг.
     * @param token токен в строки.
     * @return токен в виде объекта
     * @throws ParseException исключение во время парсинга токена.
     */
    public static SignedJWT parse(@NonNull final String token) throws ParseException
    {
        return SignedJWT.parse(token);
    }

    /**
     * @param jwt объект jwt-токена.
     * @param secret секретный ключ, хранящайся на сервере.
     * @return true, если токен варифицирован, иначе false.
     * @throws JOSEException исключение при неверной сигнатуре токена.
     */
    private static boolean verifyHMACToken(
            @NonNull final SignedJWT jwt,
            @NonNull final String secret) throws JOSEException
    {
        final JWSVerifier verifier = new MACVerifier(secret);
        return jwt.verify(verifier);
    }

    /**
     * @param authorities коллекция ролей пользователя.
     * @return коллекция {@link GrantedAuthority} в виде строки.
     */
    private static String AuthorityListToCommaSeparatedString(
            @NonNull final Collection<? extends GrantedAuthority> authorities)
    {
        final Set<String> authoritiesAsSetOfString = AuthorityUtils.authorityListToSet(authorities);
        return StringUtils.join(authoritiesAsSetOfString, ", ");
    }

    /**
     * @param jwt объект jwt-токена.
     * @return логин пользователя.
     * @throws ParseException исключение, возникающее при парсинге {@link SignedJWT}.
     */
    public static String getUsername(@NonNull final SignedJWT jwt) throws ParseException
    {
        return (String) jwt.getJWTClaimsSet().getClaims().get(SecurityConstants.LOGIN_CLAIM);
    }

    /**
     * @param jwt объект jwt-токена.
     * @return коллекция ролей пользователя.
     * @throws ParseException исключение, возникающее при парсинге {@link SignedJWT}.
     */
    public static Collection<? extends GrantedAuthority> getRoles(@NonNull final SignedJWT jwt) throws ParseException
    {
        final String roles = jwt.getJWTClaimsSet().getStringClaim(SecurityConstants.ROLES_CLAIM);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
    }

    /**
     * @param expirationInMinutes время окончания токена в минутах.
     * @return {@link Date}.
     */
    private static Date expirationDate(@NonNull final int expirationInMinutes)
    {
        return new Date(System.currentTimeMillis() + expirationInMinutes * 60 * 1000);
    }
}

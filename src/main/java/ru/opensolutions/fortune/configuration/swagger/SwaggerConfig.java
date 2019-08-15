package ru.opensolutions.fortune.configuration.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import ru.opensolutions.fortune.util.enums.AuthOptionType;
import ru.opensolutions.fortune.util.marker.SwaggerMarker;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.HashSet;

/**
 * Swagger документация - настройка.
 * <p>
 * Swagger аддрес: http://localhost:8080/fortune/api/swagger-ui.html
 */
@Configuration
@ComponentScan(basePackages = "ru.opensolutions.fortune.controller")
@EnableSwagger2
@Lazy
public class SwaggerConfig {

    private static final String TITLE = "Документация по используемым ресурсам на сервере для Fortune";
    private static final String GROUP_NAME = "Сервис по обработке идентификатора транзакции";
    private static final String PREFIX_AUTH = "Bearer";
    private static final String TYPE_API = "header";

    @Value("${swagger.project.version}")
    private String version;

    @Value("${auth.switch}")
    private String authSwitcher;

    /**
     * Настройка UI сваггера.
     * @return {@link Docket}.
     */
    @Bean
    public Docket api() {
        switch (AuthOptionType.getEnum(authSwitcher)) {
            case ON:
                return new Docket(DocumentationType.SWAGGER_2)
                        .securitySchemes(Collections.singletonList(
                                new ApiKey(PREFIX_AUTH, HttpHeaders.AUTHORIZATION, TYPE_API)
                        ))
                        .securityContexts(Collections.singletonList(
                                SecurityContext.builder()
                                        .securityReferences(
                                                Collections.singletonList(SecurityReference.builder()
                                                        .reference(PREFIX_AUTH)
                                                        .scopes(new AuthorizationScope[0])
                                                        .build()
                                                )
                                        )
                                        .build())
                        )
                        .select()
                        .apis(
                                RequestHandlerSelectors.withClassAnnotation(RestController.class))

                        .paths(PathSelectors.any())
                        .build()
                        .apiInfo(apiInfo())
                        .groupName(GROUP_NAME)
                        .produces(
                                new HashSet<>(Collections.singletonList(MediaType.APPLICATION_JSON_VALUE))
                        )
                        .consumes(
                                new HashSet<>(Collections.singletonList(MediaType.APPLICATION_JSON_VALUE))
                        );
            case OFF:
                return new Docket(DocumentationType.SWAGGER_2)
                        .select()
                        .apis(RequestHandlerSelectors.withClassAnnotation(SwaggerMarker.class))
                        .paths(PathSelectors.any())
                        .build()
                        .apiInfo(apiInfo())
                        .groupName(GROUP_NAME)
                        .produces(
                                new HashSet<>(Collections.singletonList(MediaType.APPLICATION_JSON_VALUE))
                        )
                        .consumes(
                                new HashSet<>(Collections.singletonList(MediaType.APPLICATION_JSON_VALUE))
                        );
            default:
                throw new IllegalArgumentException("Unexpected value for authSwitcher: " + authSwitcher);
        }
    }

    /**
     * @return Объекет, содержащий информацию об апи.
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(TITLE)
                .version(version)
                .build();
    }
}


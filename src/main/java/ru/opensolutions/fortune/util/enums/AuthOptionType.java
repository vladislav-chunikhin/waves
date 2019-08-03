package ru.opensolutions.fortune.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Опции для авторизации. */
@AllArgsConstructor
public enum AuthOptionType {

    ON("on"),
    OFF("off");

    /**
     * Наименованиее опции. */
    @Getter
    private String name;

    /**
     * Получение enum по наименованию.
     * @param name имя функциию.
     * @return {@link AuthOptionType}. */
    public static AuthOptionType getEnum(String name) {
        return Arrays.stream(AuthOptionType.values())
                .filter(it -> it.getName().equals(name))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                String.format("Unexpected type: %s ", name))
                );
    }
}

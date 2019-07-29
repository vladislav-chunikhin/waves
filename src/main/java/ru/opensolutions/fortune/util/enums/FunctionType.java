package ru.opensolutions.fortune.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Тип вызываемой функции. */
@AllArgsConstructor
public enum FunctionType {

    CHECK_SIGN("checkSign"),
    WITHDRAW("withdraw"),
    BET("bet");

    /**
     * Наименование функции. */
    @Getter
    private String name;

    /**
     * Получение enum по наименовании функции.
     * @param name имя функциию.
     * @return {@link FunctionType}. */
    public static FunctionType getEnum(String name) {
        return Arrays.stream(FunctionType.values())
                .filter(en -> en.getName().equals(name))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                String.format("Unexpected function type: %s ", name))
                );
    }
}

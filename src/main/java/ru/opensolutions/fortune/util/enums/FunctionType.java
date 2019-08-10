package ru.opensolutions.fortune.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;

/**
 * Тип вызываемой функции. */
@AllArgsConstructor
public enum FunctionType {

    /**
     * Проверка подписи. */
    CHECK_SIGN("checkSign"),

    /**
     * Изымать данные из транзакции. */
    WITHDRAW("withdraw"),

    /**
     * Ставка. */
    BET("bet");

    /**
     * Наименование функции. */
    @Getter
    private String name;

    /**
     * Получение enum по наименовании функции.
     * @param name имя функциию.
     * @return {@link FunctionType}. */
    public static FunctionType getEnum(@NonNull final String name) {
        return Arrays.stream(FunctionType.values())
                .filter(it -> it.getName().equals(name))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                String.format("Unexpected function type: %s ", name))
                );
    }
}

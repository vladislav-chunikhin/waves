package ru.opensolutions.fortune.util.enums;

import java.util.Arrays;

/**
 * Тип вызываемой функции. */
public enum FunctionType {
    CHECK_SIGN("checkSign"),
    WITHDRAW("withdraw");

    private String name;

    FunctionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

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

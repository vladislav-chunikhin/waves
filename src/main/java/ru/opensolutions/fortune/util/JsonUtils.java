package ru.opensolutions.fortune.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.NonNull;
import lombok.SneakyThrows;

/**
 * Класс для работы с json. */
public final class JsonUtils {

    /**
     * Преобразование json в красивый вид.
     * @param json json в одну строку.
     * @return Красивый json.
     */
    public static String getPrettyJson(@NonNull final String json) {
        final Gson gson =
                new GsonBuilder().setPrettyPrinting().create();
        final JsonParser jp = new JsonParser();
        final JsonElement je = jp.parse(json);
        return gson.toJson(je);
    }

    /**
     * Получение json из Object.
     * @param obj Объект.
     * @param isPretty флаг на красивость json.
     * @return json строка. */
    @SneakyThrows(JsonProcessingException.class)
    public static String getJsonFromObject(@NonNull final Object obj,
                                           @NonNull final boolean isPretty) {
        final ObjectMapper objectMapper = new ObjectMapper();
        final String json = objectMapper.writeValueAsString(obj);
        return isPretty ? getPrettyJson(json) : json;
    }
}

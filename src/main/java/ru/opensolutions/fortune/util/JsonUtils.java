package ru.opensolutions.fortune.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.NonNull;

/**
 * Класс для работы с json. */
public final class JsonUtils {


    /**
     * Запрещаем создавать эксземпляр класса. */
    private JsonUtils() {
        throw new RuntimeException();
    }

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
    public static String getJsonFromObject(@NonNull final Object obj,
                                           @NonNull final boolean isPretty)
    {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            final String json = objectMapper.writeValueAsString(obj);
            return isPretty ? getPrettyJson(json) : json;
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}

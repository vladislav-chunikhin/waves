package ru.opensolutions.fortune.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Класс для работы с json. */
public final class JsonUtils {

    /**
     * Преобразование json в красивый вид.
     * @param json json в одну строку.
     * @return Красивый json.
     */
    public static String getPrettyJson(String json){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(json);
        return gson.toJson(je);
    }
}

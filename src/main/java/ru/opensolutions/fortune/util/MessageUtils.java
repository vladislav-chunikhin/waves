package ru.opensolutions.fortune.util;

import lombok.NonNull;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *  Утильный класс для получения текста из properties файлов. */
public final class MessageUtils {

    /**
     * Запрещаем создавать эксземпляр класса. */
    private MessageUtils() {
        throw new RuntimeException();
    }

    /**
     * Сервис, обеспечивающий доступ к properties файлу, для получение сообщений. */
    private static final ResourceBundle RESOURCE;

    /**
     * Название properties файла с сообщениями. */
    private static final String EXCEPTION_PROPERTIES_FILE_NAME = "message";

    static {
        RESOURCE = ResourceBundle.getBundle(EXCEPTION_PROPERTIES_FILE_NAME, Locale.getDefault());
    }

    /**
     * Мэп, кеширующий сообщения, доступ к которым уже был получен. */
    private static final HashMap<String, String> EXCEPTION_MESSAGES = new HashMap<>();


    /**
     * Достаёт сообщение по ключу
     * и формирует текст сообщения, используя параметры.
     * @param templateKey ключ к шаблон-сообщению в properties файле.
     * @param params параметры сообщения.
     * @return ообщение, соответствующее переданному ключу с заданными параметрами.
     */
    public static String getMessage(@NonNull final String templateKey, final Object... params) {
        final String message  = getMessage(templateKey);
        if (params.length > 0) {
            return String.format(message, params);
        } else {
            return message;
        }
    }

    /**
     * Достаёт сообщение для исключения, по ключу.
     * @param templateKey ключ для полученния доступа к сообщению в properties файле.
     * @return сообщение, соответствующее переданному ключу.
     */
    private static String getMessage(@NonNull final String templateKey) {
        EXCEPTION_MESSAGES.computeIfAbsent(templateKey, MessageUtils::getString);
        return EXCEPTION_MESSAGES.get(templateKey);
    }

    /**
     * @param key ключ сообщения исключения.
     * @return сообщение исключения, найденное по заданному ключу и перекодированное из ISO-8859-1 в UTF-8.
     */
    private static String getString(@NonNull final String key) {
        final String message = RESOURCE.getString(key);
        return new String(message.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
}

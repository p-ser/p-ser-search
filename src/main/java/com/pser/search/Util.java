package com.pser.search;

import co.elastic.clients.json.JsonData;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.annotations.DateFormat;

public class Util {
    public static String toFormattedString(LocalDate localDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateFormat.date.getPattern());
        return localDate.format(dateTimeFormatter);
    }

    public static String toFormattedString(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateFormat.date_hour_minute.getPattern());
        return localDateTime.format(dateTimeFormatter);
    }

    public static JsonData toJsonData(LocalDate localDate) {
        String formattedString = toFormattedString(localDate);
        return JsonData.of(formattedString);
    }

    public static JsonData toJsonData(LocalDateTime localDateTime) {
        String formattedString = toFormattedString(localDateTime);
        return JsonData.of(formattedString);
    }

    public static int getIntProperty(Environment env, String key) {
        String value = Objects.requireNonNull(env.getProperty(key));
        return Integer.parseInt(value);
    }

    public static Long getLongProperty(Environment env, String key) {
        String value = Objects.requireNonNull(env.getProperty(key));
        return Long.parseLong(value);
    }

    public static boolean getBooleanProperty(Environment env, String key) {
        String value = Objects.requireNonNull(env.getProperty(key));
        return Boolean.parseBoolean(value);
    }

    public static boolean isNullOrBlank(String value) {
        if (value == null) {
            return true;
        }
        return value.isBlank();
    }
}

package common;

import java.util.function.Function;

public class Environment {

    public static int parseIntOrDefault(String value, int defaultValue) {
        return parseOrDefault(value, Integer::parseInt, defaultValue);
    }

    public static String parseStringOrDefault(String value, String defaultValue) {
        return parseOrDefault(value, String::toString, defaultValue);
    }

    private static <T> T parseOrDefault(String value, Function<String, T> converter, T defaultValue) {
        T returnValue = defaultValue;

        if (value != null) {
            returnValue = converter.apply(value);
        }
        return returnValue;
    }
}

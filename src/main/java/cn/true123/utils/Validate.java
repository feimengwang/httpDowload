package cn.true123.utils;


import java.util.List;

public class Validate {
    public static <T extends Object> void isNotNull(T expression, String message) {
        if (expression == null) {
            throw new RuntimeException(message);
        }
    }

    public static <T extends List> void isNotEmpty(T list, String message) {
        if (list == null || list.size() == 0) {
            throw new RuntimeException(message);
        }
    }

    public static void isValide(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

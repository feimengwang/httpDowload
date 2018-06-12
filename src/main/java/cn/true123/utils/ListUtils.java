package cn.true123.utils;


import java.util.List;

public class ListUtils {
    public static boolean isNotEmpty(List list) {
        return list != null && list.size() > 0;
    }
}

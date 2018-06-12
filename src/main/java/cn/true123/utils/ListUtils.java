package cn.true123.utils;


import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    public static boolean isNotEmpty(List list) {
        return list != null && list.size() > 0;
    }

    public static <T> List<T> toList(long size, int count, DLFunction<T> function) {
        List<T> result = new ArrayList<>();
        if (size > 0) {
            if (count == 0) count = Constants.COUNT;
            long mod = size % count;
            long subSize = size / count;
            if (mod > 0) {
                subSize++;
            }
            for (int i = 1; i <= count; i++) {
                long s = subSize*(i-1);
                long e = (i==count)?size:(s+subSize);
                T t= function.apply(s,e);
                result.add(t);
            }
        }
        return result;
    }
}

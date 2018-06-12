package cn.true123.utils;

@FunctionalInterface
public interface DLFunction<R> {

    public R apply(long s,long e);
}

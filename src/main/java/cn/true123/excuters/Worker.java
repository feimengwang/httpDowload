package cn.true123.excuters;

public interface Worker extends Runnable, Cloneable {
    public void close();

    public long getRemainder();

    public void flush();

    public void cancel();
}

package cn.true123.download;

public interface DownLoaderListener {
    public void finish();

    public void onUpgrade();

    public void onError(String s);
}

package cn.true123.listener;


public interface DownloadListener {
    public void finish();

    public void progress(float progress);

    public void onError(String s);
}

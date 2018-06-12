package cn.true123.download;


import cn.true123.listener.DownloadListener;

public interface DownLoadInterface {
    public void start();

    public void pause();

    public void cancel();

    public void setUrl(String url);

    public void setThreadCount(int count);

    public void setOnDownLoadListener(DownloadListener listener);
}

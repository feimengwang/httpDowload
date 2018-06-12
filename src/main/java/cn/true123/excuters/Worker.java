package cn.true123.excuters;

import cn.true123.listener.DownloadListener;
import cn.true123.listener.WorkerListener;

public interface Worker extends Runnable, Cloneable {
    public String getId();

    public void flush();

    public void cancel();

    public void setWorkerListener(WorkerListener listener);
}

package cn.true123.download;

import cn.true123.excuters.DownLoadExecutor;
import cn.true123.excuters.DownLoadWorker;
import cn.true123.excuters.SingleThreadDownLoadWorker;
import cn.true123.excuters.Worker;
import cn.true123.files.PropertiesFileLoader;
import cn.true123.files.TempFile;
import cn.true123.files.TempFileFactory;
import cn.true123.listener.DownloadListener;
import cn.true123.utils.ListUtils;
import cn.true123.utils.StringUtil;
import cn.true123.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DownLoaderFactory implements DownLoadInterface,
        DownloadListener {
    private String url;
    private DownLoaderListener listener;
    private int count = 5;
    private List<DL> dlList = new ArrayList<>();
    private List<Worker> threadList = new ArrayList<>();
    private long fileSize;
    private DLModel dlModel;
    DownLoadExecutor executor;

    public void setCount(int count) {
        this.count = count;
    }

    public static DownLoaderFactory newInstance() {
        return new DownLoaderFactory();
    }

    private DownLoaderFactory() {
        String ct = PropertiesFileLoader.getInstance().getProperty("thread");
        if (ct != null) {
            count = Integer.parseInt(ct);
        }
        init();
    }

    @Override
    public void finish() {

    }

    @Override
    public void progress(long progress) {

    }

    class DLThread implements Runnable {

        @Override
        public void run() {
            dlList.clear();
            if (!dlModel.isSupportAcceptRanges()) {
                DL dl = new DL(0, dlModel.getSize(), dlModel.getUrl());
                if (!dlList.contains(dl)) {
                    dlList.add(dl);
                }
                SingleThreadDownLoadWorker thread = new SingleThreadDownLoadWorker(dl);
                if (!threadList.contains(thread)) {
                    threadList.add(thread);
                }
                startWorker(threadList);
                return;
            }
            TempFile tempFile = TempFileFactory.getInstance().getTemFile(Utils.getFileName(url));
            List<String> lines = tempFile.getExistDownloads();
            List<DL> dls = Utils.getDLList(lines);


            if (ListUtils.isNotEmpty(dls)) {
                for (DL dl : dls) {
                    Worker thread = new DownLoadWorker(dl);
                    if (!threadList.contains(thread)) {
                        threadList.add(thread);
                    }
                    if (!dlList.contains(dl)) {
                        dlList.add(dl);
                    }
                }
                startWorker(threadList);
            } else {
                if (fileSize > 0) {
                    dlList = ListUtils.toList(fileSize, count, (s, e) -> new DL(s, e, url));
                    dlList.stream().forEach(item -> {
                        Worker thread = new DownLoadWorker(item);
                        if (!threadList.contains(thread)) {
                            threadList.add(thread);
                        }
                    });
                    startWorker(threadList);
                }
            }
        }
    }

    private void startWorker(List<Worker> threadList) {
        executor = DownLoadExecutor.instance().newFixedThreadPool();
        for (Worker thread : threadList) {
            executor.execute(thread);
        }
    }


    protected void run() {
        new Thread(new DLThread()).start();
    }

    @Override
    public void start() {
        init();
        if (!checkPath()) {
            if (listener != null) {
                listener.onError("please set the down load path!");

            }
            return;
        }
        if (fileSize == 0) {
            if (listener != null) {
                listener.onError("Can not get the file size.");
            }
            return;
        }
        run();
    }

    private boolean checkPath() {
        String path = PropertiesFileLoader.getInstance().getProperty(StringUtil.getPath("path"));
        return StringUtil.isNotEmpty(path);
    }

    private void init() {
        dlModel = Utils.getModel(url);
        fileSize = dlModel.getSize();
    }

    @Override
    public void pause() {
        threadCancle();

    }

    @Override
    public void cancel() {
        threadCancle();

    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setOnDownLoadListener(DownLoaderListener listener) {
        this.listener = listener;
    }

    private void threadCancle() {
        for (Worker thread : threadList) {
            thread.cancel();
        }
        executor.shutdown();
    }


    @Override
    public void setThreadCount(int count) {
        this.count = count;

    }


}

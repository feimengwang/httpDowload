package cn.true123.download;

import cn.true123.excuters.DownLoadExecutor;
import cn.true123.excuters.DownLoadWorker;
import cn.true123.excuters.SingleThreadDownLoadWorker;
import cn.true123.excuters.Worker;
import cn.true123.files.PropertiesFile;
import cn.true123.files.PropertiesFileLoader;
import cn.true123.files.TempFile;
import cn.true123.files.TempFileFactory;
import cn.true123.listener.DownloadListener;
import cn.true123.listener.WorkerListener;
import cn.true123.utils.ListUtils;
import cn.true123.utils.PropertiesUtils;
import cn.true123.utils.StringUtil;
import cn.true123.utils.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DownLoaderFactory implements DownLoadInterface, WorkerListener {
    private String url;
    private DownloadListener listener = null;
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
    }

    @Override
    public void onUpgrade() {
        synchronized (dlList) {
            long size = 0;
            for (DL dl : dlList) {
                size += (dl.getE() - dl.getS());
            }
            if (listener != null) {
                listener.progress(((float) (fileSize - size)) / fileSize);
            }
        }
    }

    @Override
    public void finish(String id) {
        synchronized (dlList) {
            Iterator<DL> it = dlList.iterator();
            while (it.hasNext()) {
                if (it.next().getId().equals(id)) {
                    it.remove();
                }
            }
        }
        if (dlList.size() == 0) {
            executor.shutdown();
        }
        if (listener != null) {
            listener.finish();
        }
    }


    public void start0() {
        dlList.clear();
        System.out.println("running0...");
        System.out.println(dlModel);
        if (!dlModel.isSupportAcceptRanges()) {
            DL dl = new DL(0, dlModel.getSize(), dlModel.getUrl());
            if (!dlList.contains(dl)) {
                dlList.add(dl);
            }
            Worker thread = new SingleThreadDownLoadWorker(dl);
            if (!threadList.contains(thread)) {
                threadList.add(thread);
            }
            startWorker(threadList);
            return;
        }
        System.out.println(Utils.getFileName(url));
        TempFile tempFile = TempFileFactory.getInstance().getTemFile(Utils.getFileName(url));
        System.out.println(tempFile);
        List<String> lines = tempFile.getExistDownloads();
        List<DL> dls = Utils.getDLList(lines);

        System.out.println(dls + ";dls");
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
                //String path = PropertiesFileLoader.getInstance().getProperty(StringUtil.getPath("path"));
                dlList = ListUtils.toList(fileSize, count, (s, e) -> new DL(s, e, url));
                dlList.stream().forEach(item -> {

                    item.setFileName(Utils.getFileName(item.getUrl()));
                    Worker thread = new DownLoadWorker(item);
                    if (!threadList.contains(thread)) {
                        threadList.add(thread);
                    }
                });
                startWorker(threadList);
            }
        }
    }

    private void startWorker(List<Worker> threadList) {
        executor = DownLoadExecutor.instance().newFixedThreadPool();
        System.out.println(threadList.size());
        for (Worker thread : threadList) {
            thread.setWorkerListener(this);
            executor.execute(thread);
        }
    }


    protected void run() {
        start0();
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
        String path = PropertiesUtils.getPath();
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

    @Override
    public void setOnDownLoadListener(DownloadListener listener) {
        this.listener = listener;
    }


}

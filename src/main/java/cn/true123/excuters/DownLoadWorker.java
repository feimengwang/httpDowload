package cn.true123.excuters;

import cn.true123.download.DL;
import cn.true123.files.MRandomAccessFile;
import cn.true123.files.PropertiesFileLoader;
import cn.true123.files.TempFile;
import cn.true123.files.TempFileFactory;
import cn.true123.httpClient.DownLoadHttpClient;
import cn.true123.httpClient.HttpGet;
import cn.true123.httpClient.IHttpResponse;
import cn.true123.listener.DownloadListener;
import cn.true123.listener.WorkerListener;
import cn.true123.utils.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class DownLoadWorker implements Worker {
    protected DL dl;
    protected WorkerListener listener;
    protected boolean run = true;
    protected TempFile tempFile;

    public DownLoadWorker(DL dl) {
        this.dl = dl;
    }

    @Override
    public void run() {
        DownLoadHttpClient client = DownLoadHttpClient.getInstance();
        while ((dl.getS() < dl.getE()) && run) {
            try {
                HttpGet get = new HttpGet(dl.getUrl());

                Map property = new HashMap();

                property.put("RANGE", "bytes=" + dl.getS() + "-");

                IHttpResponse res = client.exec(get, property);
                if (206 != res.getStatusCode() || 200 == res.getStatusCode()) {
                    continue;
                }
                InputStream in = res.getInputStream();
                byte[] b = new byte[1024];
                int length;
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                while (run && (length = in.read(b)) > 0 && dl.getS() < dl.getE()) {
                    byteBuffer.clear();
                    byteBuffer.put(b,0,length);
                    String path = PropertiesFileLoader.getInstance().getProperty(StringUtil.getPath("path"));
                    MRandomAccessFile.getInstance(path+dl.getFileName(), dl.getS()).write(byteBuffer).close();
                    dl.setS(dl.getS() + length);
                    flush();
                }
                res.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
            if (dl.getS() >= dl.getE()) {
                if (listener != null) {
                    listener.finish(dl.getId());
                }
            }
        }
    }


    @Override
    public String getId() {
        if(dl != null) return dl.getId();
        return "";
    }

    @Override
    public void flush() {
        if (tempFile == null) {
            System.out.println(TempFileFactory.getInstance());
            tempFile = TempFileFactory.getInstance().getTemFile(dl.getFileName());
        }
        if (tempFile != null) tempFile.setProperty(dl.getId(), dl.toString());
        if(listener != null) listener.onUpgrade();
    }

    @Override
    public void cancel() {
        run = false;
        flush();
    }

    @Override
    public void setWorkerListener(WorkerListener listener) {
        this.listener = listener;
    }
}

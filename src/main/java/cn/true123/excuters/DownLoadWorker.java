package cn.true123.excuters;

import cn.true123.download.DL;
import cn.true123.files.MRandomAccessFile;
import cn.true123.files.TempFile;
import cn.true123.files.TempFileFactory;
import cn.true123.httpClient.DownLoadHttpClient;
import cn.true123.httpClient.HttpGet;
import cn.true123.httpClient.IHttpResponse;
import cn.true123.listener.DownloadListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class DownLoadWorker implements Worker {
    protected DL dl;
    protected DownloadListener listener;
    protected boolean run = true;
    protected TempFile tempFile;
    long remainder = 0;

    public DownLoadWorker(DL dl) {
        this.dl = dl;
    }

    @Override
    public void run() {
        DownLoadHttpClient client = DownLoadHttpClient.getInstance();
        while (dl.getS() < dl.getE() && run) {
            try {
                HttpGet get = new HttpGet(dl.getUrl());
                Map property = new HashMap();
                property.put("RANGE", "bytes=" + dl.getS() + "-");
                IHttpResponse res = client.exec(get, property);
                if (206 != res.getStatusCode() || 200 == res.getStatusCode()) {
                    continue;
                }
                InputStream in = res.getInputStream();
                byte[] b = new byte[in.available()];
                int length;
                while (run && (length = in.read(b)) > 0 && dl.getS() < dl.getE()) {
                    ByteBuffer byteBuffer = ByteBuffer.wrap(b);
                    MRandomAccessFile.getInstance(dl.getFileName(), dl.getS()).write(byteBuffer).close();
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
            if (dl.getS() <= dl.getE()) {
                if (listener != null) {
                    listener.finish();
                }
            }
        }
    }

    public DownloadListener getListener() {
        return listener;
    }

    public void setListener(DownloadListener listener) {
        this.listener = listener;
    }

    @Override
    public void close() {

    }


    @Override
    public long getRemainder() {
        return remainder;
    }

    @Override
    public void flush() {
        if (tempFile == null) {
            tempFile = TempFileFactory.getInstance().getTemFile(dl.getFileName());
        }
        if (tempFile != null) tempFile.setProperty(dl.getId(), dl.toString());
        remainder = dl.getE() - dl.getS();
    }

    @Override
    public void cancel() {
        run = false;
        flush();
    }
}

package cn.true123.excuters;

import cn.true123.download.DL;
import cn.true123.files.MRandomAccessFile;
import cn.true123.httpClient.DownLoadHttpClient;
import cn.true123.httpClient.HttpGet;
import cn.true123.httpClient.IHttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;

public class SingleThreadDownLoadWorker extends DownLoadWorker {
    public SingleThreadDownLoadWorker(DL dl) {
        super(dl);
    }

    @Override
    public void run() {
        DownLoadHttpClient client = DownLoadHttpClient.getInstance();
        try {
            HttpGet get = new HttpGet(dl.getUrl());

            IHttpResponse res = client.exec(get);
            if (200 != res.getStatusCode()) {
                return;
            }
            InputStream in = res.getInputStream();
            long available = in.available();
            byte[] b = new byte[1024];
            int length;
            MRandomAccessFile randomAccessFile = MRandomAccessFile.getInstance(dl.getFileName(), dl.getS());
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (run && (length = in.read(b)) > 0) {
                byteBuffer.flip();
                byteBuffer.put(b, 0, length);
                randomAccessFile.write(byteBuffer);
                dl.setS(dl.getS() + length);
                flush();
            }
            res.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (dl.getS() <= dl.getE()) {
            if (listener != null) {
                listener.finish();
            }
        }
    }
}

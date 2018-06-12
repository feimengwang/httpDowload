package cn.true123;

import cn.true123.download.DownLoadInterface;
import cn.true123.download.DownLoader;
import cn.true123.listener.DownloadListener;

public class T {
    public static void main(String[] args) {
        DownLoadInterface downLoadInterface = DownLoader.getInstance();
        downLoadInterface.setUrl("http://down.360safe.com/360ap/360freewifi_beta.apk");

        downLoadInterface.setOnDownLoadListener(new DownloadListener() {
            @Override
            public void finish() {
                System.out.println("finish");
            }

            @Override
            public void progress(float progress) {
                System.out.println(progress);
            }

            @Override
            public void onError(String s) {
                System.out.println(s);
            }
        });

        downLoadInterface.start();
    }
}

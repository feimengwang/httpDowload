package cn.true123.httpClient;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;


public class DownLoadHttpClient {
    public static String HEAD = "HEAD";
    public static String GET = "GET";
    public static String POST = "POST";
    private static DownLoadHttpClient instance;

    private DownLoadHttpClient() {

    }

    public static DownLoadHttpClient getInstance() {
        if (null == instance) {
            synchronized (DownLoadHttpClient.class) {
                instance = new DownLoadHttpClient();
            }
        }
        return instance;
    }

    public IHttpResponse exec(BaseHttpMethod method) throws IOException {
        return exec(method, null);
    }

    public IHttpResponse exec(BaseHttpMethod method, Map property) throws IOException {
        System.out.println("exec"+property);
        HttpURLConnection con = ConnectFactory.getInstance().getCon(method, property);
        System.out.println("con"+con);
        return new HttpResponse(con);
    }


}
package cn.true123.utils;

import cn.true123.download.DL;
import cn.true123.download.DLModel;
import cn.true123.httpClient.BaseHttpMethod;
import cn.true123.httpClient.DownLoadHttpClient;
import cn.true123.httpClient.HttpHead;
import cn.true123.httpClient.IHttpResponse;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static DLModel getModel(String surl) {
        DLModel mDLModel = new DLModel();
        mDLModel.setUrl(surl);
        BaseHttpMethod head = new HttpHead(surl);
        DownLoadHttpClient client = DownLoadHttpClient.getInstance();
        IHttpResponse response = null;
        try {
            response = client.exec(head);
            if (response != null) {
                String size = response.getHeader("Content-Length");
                long lsiz = size != null && !"".equals(size) ? Long.parseLong(size) : 0l;
                mDLModel.setSize(lsiz);
                String acceptRanges = response.getHeader("Accept-Ranges");
                mDLModel.setAcceptRanges(acceptRanges);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return mDLModel;
    }


    public static String MD5(String msg) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = msg.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    public static String getFileName(String url) {
        String fileName = "";
        if (url != null) {
            int indexOf = url.indexOf("\\?");

            if (indexOf >= 0) {
                url = url.substring(0, indexOf);
            }
            int split = url.lastIndexOf('/');
            if (split > -1 && split < url.length()) {
                fileName = url.trim().substring(split + 1);
            }
        }
        return fileName;
    }

    public static List<DL> getDLList(List<String> values) {
        List<DL> DLs = new ArrayList<>();
        for (String v : values) {
            String[] kvs = v.split(";=;=");
            if (kvs != null && kvs.length > 1) {
                DLs.add(new DL(kvs[1]));
            }
        }
        return DLs;
    }
}

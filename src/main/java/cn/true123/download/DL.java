package cn.true123.download;

import cn.true123.DLException;
import cn.true123.utils.StringUtil;
import cn.true123.utils.Utils;

import java.io.Serializable;

public class DL implements Serializable{
    private String url;
    private long s;
    private long e;
    private String id;
    private String fileName;

    public DL(long s, long e, String url) {
        this.s = s;
        this.e = e;
        this.url = url;
        id = Utils.MD5(url + "" + s + e);
    }

    public DL(String line) {
        if (StringUtil.isNotEmpty(line) && line.indexOf(";") >= 0) {
            String[] split = line.split(";");
            if (split.length > 2) {
                this.s = Integer.parseInt(split[1]);
                this.e = Integer.parseInt(split[2]);
                this.url = split[0];
                id = Utils.MD5(url + "" + s + e);
            }
        } else {
            throw new DLException("输入格式不正确，不能实例化DL！");
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DL other = (DL) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getS() {
        return s;
    }

    public void setS(long s) {
        this.s = s;
    }

    public long getE() {
        return e;
    }

    public void setE(long e) {
        this.e = e;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {

        return url + ";" + s + ";" + e;
    }

}

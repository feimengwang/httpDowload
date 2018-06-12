package cn.true123.files;

import cn.true123.utils.StringUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class TempFileFactory {
    private  Map<String, TempFile> fileMap = new HashMap<>();

    private static class Holder {
        private static TempFileFactory factory = new TempFileFactory();
    }

    private TempFileFactory() {

    }

    public static TempFileFactory getInstance() {
        return Holder.factory;
    }

    public  synchronized TempFile getTemFile(String fileName) {
        if (fileMap.get(fileName) == null) {
            String filePath = PropertiesFileLoader.getInstance().getProperty(StringUtil.getPath("path"));
            File file = new File(filePath);
            if(!file.exists()){
                file.mkdirs();
            }
            fileMap.put(fileName, TempFile.getInstance(filePath+fileName));
        }
        return fileMap.get(fileName);
    }
}

package cn.true123.files;


import cn.true123.utils.FIleUtil;
import cn.true123.utils.StringUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public final class TempFile extends PropertiesFileImpl{
    Properties properties = new Properties();
    protected TempFile(InputStream inputStream) {
        super(inputStream);
    }
    public static TempFile getInstance(String fileName){
        try {
            File file = new File(fileName);
            FIleUtil.createFile(file);
            InputStream inputStream = new FileInputStream(file);
            return new TempFile(inputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<String> getExistDownloads(){
        String ids = getProperty("allKey");
        List<String> values = new ArrayList<>();
        if(StringUtil.isNotEmpty(ids)){
            String[] idsplit = ids.split(";");
            for (String s:idsplit) {
                values.add(getProperty(s));
            }
        }
        return values;
    }
}

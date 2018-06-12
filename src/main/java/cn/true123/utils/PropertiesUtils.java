package cn.true123.utils;

import cn.true123.files.PropertiesFileLoader;

public class PropertiesUtils {
    private static PropertiesFileLoader propertiesFileLoader = PropertiesFileLoader.getInstance();

    public static String getPath() {
        return propertiesFileLoader.getProperty(StringUtil.getPath(Constants.PATH));
    }

    public static String getThread() {
        return propertiesFileLoader.getProperty(Constants.TREAD_COUNT);
    }
}

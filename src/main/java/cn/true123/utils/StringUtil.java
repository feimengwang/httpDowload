package cn.true123.utils;

public class StringUtil {
    public static boolean isNotEmpty(String s) {
        return (s != null && s.trim().length() > 0);
    }

    public static boolean isEmpty(String s) {
        return (s == null || s.trim().length() == 0);
    }

    public static String getOSName() {
        String osName = System.getenv("OS");
        if (osName != null && osName.indexOf("Windows") >= 0) {
            return "win";
        } else if (osName != null) {
            return "linux";
        }
        return "win";
    }

    public static String getPath(String path) {
        String os = getOSName();
        return path + "_" + os;
    }

}

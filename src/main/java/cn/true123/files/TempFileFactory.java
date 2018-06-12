package cn.true123.files;

import java.util.HashMap;
import java.util.Map;


public class TempFileFactory {
    private static Map<String, TempFile> fileMap = new HashMap<>();

    private static class Holder {
        private static TempFileFactory factory = new TempFileFactory();
    }

    private TempFileFactory() {

    }

    public static TempFileFactory getInstance() {

        return Holder.factory;
    }

    public TempFile getTemFile(String fileName) {
        if (fileMap.get(fileName) == null) {
            fileMap.put(fileName, TempFile.getInstance(fileName));
        }
        return fileMap.get(fileName);
    }
}

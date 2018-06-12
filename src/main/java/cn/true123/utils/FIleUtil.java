package cn.true123.utils;

import cn.true123.DLException;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;


public class FIleUtil {
    public static void createFile(File file) {
        if (file != null && !file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new DLException(e.getMessage());
            }
        }
    }

    public static void deleteAndCreateFile(File file) {
        if (file != null && file.exists()) {
            file.delete();
        }
        if (file != null) {
            createFile(file);
        }
    }

    public static void deleteFile(String file) {
        File f = new File(file);
        deleteFile(f);
    }


    public static void deleteFile(File file) {
        if (file != null) {
            try {
                file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

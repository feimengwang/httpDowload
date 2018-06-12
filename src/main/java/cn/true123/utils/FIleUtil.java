package cn.true123.utils;

import java.io.File;
import java.io.IOException;


public class FIleUtil {
    public static void createFile(File file) {
        if (file != null && !file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static void deleteAndCreateFile(File file){
        if(file != null && file.exists()){
            file.delete();
        }
        if(file != null){
            createFile(file);
        }
    }
}

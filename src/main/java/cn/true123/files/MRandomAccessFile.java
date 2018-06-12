package cn.true123.files;

import cn.true123.utils.FIleUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;


public class MRandomAccessFile extends BaseFile {
    MRandomAccessFile(FileChannel fileChannel) {
        super(fileChannel);
    }

    public static MRandomAccessFile getInstance(String file, long pos) {
        try {
            File f = new File(file);
            FIleUtil.createFile(f);
            RandomAccessFile randomAccessFile = new RandomAccessFile(f, "rw");
            randomAccessFile.seek(pos);
            return new MRandomAccessFile(randomAccessFile.getChannel());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}

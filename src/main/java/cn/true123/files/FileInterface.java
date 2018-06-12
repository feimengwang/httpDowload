package cn.true123.files;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public interface FileInterface {
    public FileChannel getFileChannel();

    public void close();

}

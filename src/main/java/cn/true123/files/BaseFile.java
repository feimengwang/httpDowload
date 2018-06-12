package cn.true123.files;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public abstract class BaseFile implements FileInterface {
    private FileChannel fileChannel = null;

    BaseFile(FileChannel fileChannel) {
        this.fileChannel = fileChannel;
    }

    @Override
    public FileChannel getFileChannel() {
        return fileChannel;
    }

    public int read(ByteBuffer byteBuffer) throws IOException {
        return fileChannel.read(byteBuffer);
    }

    public BaseFile write(ByteBuffer byteBuffer) throws IOException {
        fileChannel.write(byteBuffer);
        return this;
    }

    public BaseFile write(ByteBuffer byteBuffer, long position) throws IOException {
        fileChannel.write(byteBuffer, position);
        return this;
    }

    @Override
    public void close() {
        if (fileChannel != null) {
            try {
                fileChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

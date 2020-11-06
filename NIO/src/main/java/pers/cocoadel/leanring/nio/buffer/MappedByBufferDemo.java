package pers.cocoadel.leanring.nio.buffer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 *可以让文件直接在内存（堆外内存）中进行修改，而如何同步到文件有NIO来完成
 */
public class MappedByBufferDemo {
    public static void main(String[] args) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile("./demo1.text","rw");
        FileChannel fileChannel = accessFile.getChannel();
        /**
         * 参数1：FileChannel.MapMode.READ_WRITE 使用读写模式
         * 参数2：6：可以直接修改的起始位置,下标从0开始
         * 参数3：5：修改的长度
         */
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE,6,5);
        mappedByteBuffer.put(0, (byte) 'W');
        mappedByteBuffer.put(1, (byte) 'e');
        mappedByteBuffer.put(2, (byte) 'i');
        mappedByteBuffer.put(3, (byte) 's');
        mappedByteBuffer.put(4, (byte) 's');

        accessFile.close();
    }
}

package pers.cocoadel.java.learning.io.file;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.CRC32;

public class FileChannelDemo {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get(String.format("D:%sJava%sjre%slib%srt.jar",
                File.separator, File.separator, File.separator, File.separator));
        long startTime = System.currentTimeMillis();
        long value = checksumInputStream(path);
        long time = System.currentTimeMillis() - startTime;
        System.out.println("checksumInputStream time = " + time + " value: " + value);

        startTime = System.currentTimeMillis();
        value = checksumBufferInputStream(path);
        time = System.currentTimeMillis() - startTime;
        System.out.println("checksumBufferInputStream time = " + time + " value: " + value);

        startTime = System.currentTimeMillis();
        value = checksumRandomAccessFile(path);
        time = System.currentTimeMillis() - startTime;
        System.out.println("checksumRandomAccessFile time = " + time + " value: " + value);

        startTime = System.currentTimeMillis();
        value = checksumFileChannel(path);
        time = System.currentTimeMillis() - startTime;
        System.out.println("checksumFileChannel time = " + time + " value: " + value);
    }

    /**
     * 直接通过输入流计算文件的校验和
     */
    private static long checksumInputStream(Path fileName) throws IOException {
        try (InputStream inputStream = Files.newInputStream(fileName)) {
            CRC32 crc32 = new CRC32();
            int c = 0;
            while ((c = inputStream.read()) != -1) {
                crc32.update(c);
            }
            return crc32.getValue();
        }
    }

    /**
     * 通过缓存的输入流计算文件的校验和
     */
    private static long checksumBufferInputStream(Path fileName) throws IOException {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(Files.newInputStream(fileName))) {
            CRC32 crc32 = new CRC32();
            int c = 0;
            while ((c = bufferedInputStream.read()) != -1) {
                crc32.update(c);
            }
            return crc32.getValue();
        }
    }

    /**
     * 通过随机读取计算文件校验和
     */
    private static long checksumRandomAccessFile(Path fileName) throws IOException {
        try (RandomAccessFile r = new RandomAccessFile(fileName.toFile(), "r")) {
            CRC32 crc32 = new CRC32();
            int c = 0;
            long len = r.length();
            for (long i = 0; i < len; i++) {
                r.seek(i);
                c = r.readByte();
                crc32.update(c);
            }
            return crc32.getValue();
        }
    }

    /**
     * 使用文件内存映射来计算校验和
     */
    private static long checksumFileChannel(Path filename) throws IOException {
        try (FileChannel channel = FileChannel.open(filename)) {
            CRC32 crc32 = new CRC32();
            long len = channel.size();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, len);
            for (int i = 0; i < len; i++) {
                int c = buffer.get(i);
                crc32.update(c);
            }
            return crc32.getValue();
        }
    }
}

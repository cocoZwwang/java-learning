package pers.cocoadel.leanring.nio.buffer;



import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * 文件NIO的一些Demo
 */
public class FileNioDemo {
    public static void main(String[] args) {
        writeString2File("./demo1.text","Hello ruby!");
        copyFile("./demo1.text","./demo2.text");
        readFile2Console("./demo2.text");
        copyByTransferFrom("./cat.jpg","./cat-copy.jpg");
    }

    /**
     * 写入内容到指定文件，如果不存在就创建一个
     */
    private static void writeString2File(String filePath, String content){
        try (FileOutputStream fos = new FileOutputStream(filePath);
             FileChannel fileChannel = fos.getChannel();){
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put(content.getBytes());
            byteBuffer.flip();
            fileChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取指定文件内容并且打印到控制台
     */
    private static void readFile2Console(String filePath){
        File file = new File(filePath);
        try (FileInputStream fis = new FileInputStream(file);
             FileChannel fileChannel = fis.getChannel();) {
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
            fileChannel.read(byteBuffer);
            byteBuffer.flip();
            byte[] bytes = new byte[byteBuffer.remaining()];
            byteBuffer.get(bytes);
            System.out.println(new String(bytes, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拷贝源文件到目标文件
     */
    private static void copyFile(String src,String dst){
        File file = new File(src);
        try (FileInputStream srcFis = new FileInputStream(file);
             FileOutputStream dstFos = new FileOutputStream(dst);) {
            FileChannel srcChannel = srcFis.getChannel();
            FileChannel dstChannel = dstFos.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
            while(true){
                byteBuffer.clear();
                int readBytes = srcChannel.read(byteBuffer);
                if (readBytes < 0){
                    break;
                }
                byteBuffer.flip();
                dstChannel.write(byteBuffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过transferFrom方法拷贝文件
     */
    private static void copyByTransferFrom(String src,String dst){
        File file = new File(src);
        try (FileInputStream srcFis = new FileInputStream(file);
             FileOutputStream dstFos = new FileOutputStream(dst);) {
            FileChannel srcChannel = srcFis.getChannel();
            FileChannel dstChannel = dstFos.getChannel();
            dstChannel.transferFrom(srcChannel,0,srcChannel.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

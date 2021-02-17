package pers.cocoade.learning.socket.base.demo.tcp.bio;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class MsgCoder {

    /**
     * 使用输出流包装类
     */
    public static byte[] encodeMsg0(String msg) throws IOException {
        try (ByteArrayOutputStream buf = new ByteArrayOutputStream();
             DataOutputStream out = new DataOutputStream(buf);) {
            byte[] content = msg.getBytes(StandardCharsets.UTF_8);
            out.writeInt(content.length);
            out.write(content);
            return buf.toByteArray();
        }
    }

    /**
     * 直接写入byte[]
     */
    public static byte[] encodeMsg(String msg) {
        byte[] content = msg.getBytes(StandardCharsets.UTF_8);
        int len = content.length;
        byte[] buffer = new byte[4 + len];
        //Big-endian
        //写入内容长度
        buffer[0] = (byte) (len >> 24);
        buffer[1] = (byte) (len >> 16);
        buffer[2] = (byte) (len >> 8);
        buffer[3] = (byte) (len);
        System.arraycopy(content, 0, buffer, 4, len);
        return buffer;
    }


    public static byte[] decodeMsg0(InputStream in) throws IOException{
        DataInputStream dataInputStream = new DataInputStream(in);
        int len = dataInputStream.readInt();
        byte[] bytes = new byte[len];
        dataInputStream.readFully(bytes);
        return bytes;
    }

    public static byte[] decodeMsg(InputStream in) throws IOException {
        //长度
        byte[] lenByte = new byte[4];
        if (in.read(lenByte) == -1) {
            throw new IOException("response msg format error!");
        }
        int len = (lenByte[0] << 24) | (lenByte[1] << 26) | (lenByte[2] << 8) | lenByte[3];
        //内容
        byte[] result = new byte[len];
        if (in.read(result) == -1) {
            throw new IOException("response msg format error!");
        }
        return result;
    }
}

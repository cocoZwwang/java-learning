package pers.cocoadel.learning.vote.framer;

import java.io.*;

/**
 * 基于消息长度封装的帧信息
 */
public class LengthFramer implements Framer{
    public static final int MAX_MESSAGE_LENGTH = 65535;

    public static final int BYTE_MASK = 0xFF;

    public static final int SHORT_MASK = 0xffff;

    public static final int BYTE_SHIFT = Byte.SIZE;

    private final DataInputStream din;

    public LengthFramer(InputStream din) {
        this.din = new DataInputStream(din);
    }

    @Override
    public void frameMsg(byte[] msg, OutputStream out) throws IOException {
        if (msg.length > MAX_MESSAGE_LENGTH) {
            throw new IOException("msg too long");
        }
        //写入消息长度，2个字节（big-Endian)
        out.write((msg.length >> BYTE_SHIFT) & BYTE_MASK);
        out.write(msg.length & BYTE_MASK);
        out.write(msg);
        out.flush();
    }

    @Override
    public byte[] nextMsg() throws IOException {
        //读取消息长度
        int len = 0;
        try {
             len = din.readUnsignedShort();
        } catch (EOFException e) {
            return null;
        }
        //读取消息内容
        byte[] buffer = new byte[len];
        din.readFully(buffer);
        return buffer;
    }
}

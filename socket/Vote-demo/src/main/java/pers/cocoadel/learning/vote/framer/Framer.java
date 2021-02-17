package pers.cocoadel.learning.vote.framer;

import java.io.IOException;
import java.io.OutputStream;

public interface Framer {
    /**
     * 把信息封装成帧消息，并且输出到指定的输出流
     */
    void frameMsg(byte[] msg, OutputStream out) throws IOException;

    /**
     * 扫描输入流，读取帧消息
     */
    byte[] nextMsg() throws IOException;
}

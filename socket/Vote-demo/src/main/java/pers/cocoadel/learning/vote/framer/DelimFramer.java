package pers.cocoadel.learning.vote.framer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 基于消息界定符来封装帧信息
 */
public class DelimFramer implements Framer{
    private final InputStream inputStream;

    private static final byte DELIMITER = '\n';

    public DelimFramer(InputStream inputStream) {
        this.inputStream = inputStream;
    }


    @Override
    public void frameMsg(byte[] msg, OutputStream out) throws IOException {
        //如果发送的消息体中包含界定符则抛出异常
        for(byte b : msg){
            if(b == DELIMITER){
                throw new IOException("the msg contains delimiter");
            }
        }
        out.write(msg);
        out.write(DELIMITER);
        out.flush();
    }

    @Override
    public byte[] nextMsg() throws IOException {
        ByteArrayOutputStream msgBuffer = new ByteArrayOutputStream();
        int nextByte;
        while ((nextByte = inputStream.read()) != DELIMITER) {
            if (nextByte == -1) {
                if (msgBuffer.size() == 0) {
                    return null;
                }else {
                    throw new IOException("No-empty message without delimiter");
                }
            }
            msgBuffer.write(nextByte);

        }
        return msgBuffer.toByteArray();
    }
}

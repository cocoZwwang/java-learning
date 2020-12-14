package pers.cocoade.learning.socket.tcp.bio.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class EchoClient {

    //协议：len（4 byte） + content
    public static void main(String[] args) {
        InputStream in = null;
        OutputStream out = null;
        try {
            Socket socket = new Socket("127.0.0.1",8081);
            String hello = "hello ruby!";
            in = socket.getInputStream();
            out = socket.getOutputStream();
            byte[] content = hello.getBytes(StandardCharsets.UTF_8);
            int len = content.length;
            byte[] buffer = new byte[4 + len];
            //写入内容长度
            buffer[0] = (byte) (len >> 24);
            buffer[1] = (byte) (len >> 16);
            buffer[2] = (byte) (len >> 8);
            buffer[3] = (byte) (len);
            System.arraycopy(content,0,buffer,4,len);
            out.write(buffer);
            out.flush();
            //读取返回数据
            //长度
            byte[] lenByte = new byte[4];
            in.read(lenByte);
            len = (lenByte[0] << 24) | (lenByte[1] << 26) | (lenByte[2] << 8) | lenByte[3];
            //内容
            byte[] resultBytes = new byte[len];
            in.read(resultBytes);
            String result = new String(resultBytes);
            System.out.println("result: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(out != null){
                try {
                    out.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}

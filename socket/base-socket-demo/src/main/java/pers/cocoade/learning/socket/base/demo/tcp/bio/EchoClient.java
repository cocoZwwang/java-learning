package pers.cocoade.learning.socket.base.demo.tcp.bio;


import java.io.*;
import java.net.Socket;

public class EchoClient {

    //协议：len（4 byte） + content
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 8081);
             InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream();) {
            String hello = "hello ruby!";
            byte[] buffer = MsgCoder.encodeMsg0(hello);
            out.write(buffer);
            out.flush();
            //读取返回数据
            byte[] resultBytes = MsgCoder.decodeMsg0(in);
            String result = new String(resultBytes);
            System.out.println("result: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

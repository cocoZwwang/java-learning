package pers.cocoadel.learning.socket.echo.bio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class EchoClient0 {
    private static final int port = 8080;
    private static final String host = "localhost";
    private static final String content = "Hello! I am Ruby";
    private static final Logger LOGGER = Logger.getLogger("echo client");

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public void start() throws IOException {
        socket = new Socket(host, port);//连接到目标服务器
        inputStream = socket.getInputStream();//获取socket的输入流
        outputStream = socket.getOutputStream();//获取socket的输出流
    }

    //关闭Socket的相关资源
    public void close(){
        try {
            if(socket != null) socket.close();
            if(inputStream != null) inputStream.close();
            if(outputStream != null) outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //写入消息到输出流，发送到对端
    public void write() throws IOException{
        outputStream.write(content.getBytes());
    }
    //读取对端返回的消息
    public String readNext() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len;
        int size = 0;
        byte[] buffer = new byte[content.length()];
        while (size < content.length() && (len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
            size += len;
        }
        return new String(bos.toByteArray());
    }

    public static void main(String[] args) {
        EchoClient0 echoClient0 = new EchoClient0();
        try {
            echoClient0.start();
            echoClient0.write();
            String result = echoClient0.readNext();
            LOGGER.info("receive msg: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            echoClient0.close();
        }
    }
}

package pers.cocoadel.learning.socket.echo.bio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class EchoClient {
    private static final int port = 8080;

    private static final String host = "localhost";

    private static final Logger LOGGER = Logger.getLogger("echo client");

    private int no;

    EchoClient(int no) {
        this.no = no;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            EchoClient client = new EchoClient(i);
            client.start0();

        }
    }

    private void start() {
        try {
            Socket socket = new Socket(host, port);
            InputStream ins = socket.getInputStream();
            OutputStream ops = socket.getOutputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            String content = String.format("hello %s! I am ruby!", no);
            ops.write(content.getBytes(StandardCharsets.UTF_8.name()));
            byte[] buffer = new byte[content.length()];
            int len;
            //通过size计算读取的字节，在读取size个字节后，输出并且关闭socket连接
            int size = 0;
            while (size < content.length() && (len = ins.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
                size += len;
            }
            byte[] responseBytes = bos.toByteArray();
            if (responseBytes.length > 0) {
                LOGGER.info("echo client receive msg: " + new String(responseBytes));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void start0() {
        try {
            Socket socket = new Socket(host, port);
            InputStream ins = socket.getInputStream();
            OutputStream ops = socket.getOutputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            String content = String.format("hello %s! I am ruby!", no);
            ops.write(content.getBytes(StandardCharsets.UTF_8.name()));
            //通过关闭输出流让服务端能读取到-1
            socket.shutdownOutput();
            byte[] buffer = new byte[content.length()];
            int len;
            while ((len = ins.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            byte[] responseBytes = bos.toByteArray();

            if (responseBytes.length > 0) {
                LOGGER.info("echo client receive msg: " + new String(responseBytes));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package pers.cocoade.learning.socket.tcp.bio.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class EchoServer {
    private final static int THREAD_NUMBER = Runtime.getRuntime().availableProcessors() * 2;
    private final static Executor THREAD_POOL = new ThreadPoolExecutor(THREAD_NUMBER, THREAD_NUMBER, 1, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(1000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    private final static int port = 8081;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                THREAD_POOL.execute(() -> doSocket(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //协议：len（4 byte） + content
    private static void doSocket(Socket socket) {
        try (InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream();
             ) {
            //读取
            byte[] bytes = new byte[4];
            in.read(bytes);
            int len = (bytes[0] << 24) | (bytes[1] << 26) | (bytes[2] << 8) | bytes[3];
            System.out.println("receive len: " + len);
            bytes = new byte[len];
            in.read(bytes);
            String receive = new String(bytes);
            System.out.println("receive: " + receive);
            //返回
            byte[] resultBytes = new byte[len + 4];
            System.arraycopy(bytes,0,resultBytes,4,len);
            resultBytes[0] = (byte) (len >> 24);
            resultBytes[1] = (byte) (len >> 16);
            resultBytes[2] = (byte) (len >> 8);
            resultBytes[3] = (byte) (len);
            out.write(resultBytes);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package pers.cocoade.learning.socket.base.demo.tcp.bio;

import pers.cocoade.learning.socket.base.demo.tcp.bio.MsgCoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
            byte[] bytes = MsgCoder.decodeMsg0(in);
            String receive = new String(bytes);
            System.out.println("receive: " + receive);
            //返回
            byte[] resultBytes = MsgCoder.encodeMsg0(receive);
            out.write(resultBytes);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

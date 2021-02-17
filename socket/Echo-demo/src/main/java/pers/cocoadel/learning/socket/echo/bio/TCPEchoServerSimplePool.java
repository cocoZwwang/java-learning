package pers.cocoadel.learning.socket.echo.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * 客户端连接使用线程池（一个简单线程池实现）
 */
public class TCPEchoServerSimplePool {
    private static final int port = 8080;

    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2 + 1;

    public static void main(String[] args) {
        TCPEchoServerSimplePool server = new TCPEchoServerSimplePool();
        server.start();
    }

    private void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Logger logger = Logger.getLogger("practical");
            for (int i = 0; i < THREAD_POOL_SIZE; i++) {
                Thread thread = new Thread(() -> {
                    try {
                        while (true){
                            Socket socket = serverSocket.accept();
                            EchoProtocol echoProtocol = new EchoProtocol(socket, logger);
                            echoProtocol.run();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
                logger.info("created and started the thread " + thread.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

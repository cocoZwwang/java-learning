package pers.cocoadel.learning.socket.echo.bio;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * 使用Executor作为线程池
 */
public class TCPEchoServerExecutor {
    private static final int PORT = 8080;

    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2 + 1;

    private final Executor threadPool = new ThreadPoolExecutor(THREAD_POOL_SIZE, THREAD_POOL_SIZE, 10, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(1000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {
        TCPEchoServerExecutor server = new TCPEchoServerExecutor();
        server.start();
    }

    private void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT);) {
            Logger logger = Logger.getLogger("practical");
            while (true) {
                Socket socket = serverSocket.accept();
                threadPool.execute(new TimeLimitEchoProtocol(socket, logger));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

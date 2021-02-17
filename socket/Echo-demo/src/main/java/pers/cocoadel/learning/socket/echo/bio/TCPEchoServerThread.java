package pers.cocoadel.learning.socket.echo.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * 一个客户端一个线程
 */
public class TCPEchoServerThread {
    private static final int port = 8080;

    public static void main(String[] args) {
        TCPEchoServerThread server = new TCPEchoServerThread();
        server.start();
    }

    private void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Logger logger = Logger.getLogger("practical");
            while (true) {
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(new EchoProtocol(socket,logger));
//                Thread thread = new Thread(new TimeLimitEchoProtocol(socket,logger));
                thread.start();
                logger.info("created and started the thread " + thread.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package pers.cocoadel.learning.socket.echo.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 读取客户端信息添加超时机制
 */
public class TimeLimitEchoProtocol implements Runnable{
    private static final int BUF_SIZE = 32;

    private static final int TIME_LIMIT = 1 * 1000;

    private final Socket socket;

    private final Logger logger;

    public TimeLimitEchoProtocol(Socket socket, Logger logger) {
        this.socket = socket;
        this.logger = logger;
    }


    @Override
    public void run() {
        handlerEchoClient(socket,logger);
    }

    private void handlerEchoClient(Socket clientSocket, Logger logger) {
        try (
                InputStream ins = clientSocket.getInputStream();
                OutputStream ops = clientSocket.getOutputStream();
        ) {
            int receiveSize = 0;
            int totalByteSize = 0;
            byte[] buffer = new byte[BUF_SIZE];
            int timeLimit = TIME_LIMIT;
            long timeDeadLine = System.currentTimeMillis() + timeLimit;
            clientSocket.setSoTimeout(timeLimit);
            while (timeLimit > 0 && (receiveSize = ins.read(buffer)) != -1) {
                ops.write(buffer, 0, receiveSize);
                totalByteSize += receiveSize;
                timeLimit = (int) (timeDeadLine - System.currentTimeMillis());
                clientSocket.setSoTimeout(timeLimit);
            }
            logger.info("Client " + clientSocket.getRemoteSocketAddress() + " , echoed " + totalByteSize + " bytes");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Exception in echo protocol", e);
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

package pers.cocoadel.learning.socket.echo.bio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoProtocol implements Runnable {
    private static final int BUF_SIZE = 32;

    private final Socket socket;

    private final Logger logger;

    public EchoProtocol(Socket socket, Logger logger) {
        this.socket = socket;
        this.logger = logger;
    }


    @Override
    public void run() {
        handlerEchoClient(socket,logger);
    }

    public void handlerEchoClient(Socket clientSocket, Logger logger) {
        try (InputStream is = clientSocket.getInputStream();
             OutputStream os = clientSocket.getOutputStream();) {
            int receiveSize;
            int totalByteSize = 0;
            byte[] buffer = new byte[BUF_SIZE];
            while ((receiveSize = is.read(buffer)) != -1) {
                os.write(buffer, 0, receiveSize);
                totalByteSize += receiveSize;
            }
            logger.info("Client " + clientSocket.getRemoteSocketAddress() + " , echoed " + totalByteSize + " bytes");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Exception in echo protocol", e);
        } finally {
            try {
                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

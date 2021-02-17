package pers.cocoade.learning.socket.compress;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CompressServer {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        CompressServer server = new CompressServer();
        server.start();
    }

    private void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT);){
            while (true) {
                Socket socket = serverSocket.accept();
                doSocket(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doSocket(Socket socket) {
        try (InputStream ins = socket.getInputStream();
             OutputStream ops = socket.getOutputStream();) {
            byte[] inBuf = new byte[256];
            int len;
            while ((len = ins.read(inBuf)) != -1) {
                ops.write(inBuf, 0, len);
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package pers.cocoadel.learning.socket.echo.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPEchoServer {
    private static final int port = 8080;
    private ServerSocket serverSocket;

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) {
            Socket socket = serverSocket.accept();
            //处理接收的客户端连接
            doSocket(socket);
        }
    }

    private void doSocket(Socket socket) {
        try (OutputStream outputStream = socket.getOutputStream();
             InputStream inputStream = socket.getInputStream();) {
            byte[] bytes = new byte[32];
            int len;
            //读取客户端发过来的消息，并且回显
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            if(serverSocket != null){
                serverSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TCPEchoServer server = new TCPEchoServer();
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            server.close();
        }
    }
}

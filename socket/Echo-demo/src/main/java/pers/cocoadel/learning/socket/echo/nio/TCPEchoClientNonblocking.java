package pers.cocoadel.learning.socket.echo.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class TCPEchoClientNonblocking {
    private final static int PORT = 8080;

    private final static String SERVER_IP = "localhost";

    private static final int TIME_OUT = 3000;

    public static void main(String[] args) {
        TCPEchoClientNonblocking clientNonblocking = new TCPEchoClientNonblocking();
        clientNonblocking.start();
    }

    private void start() {
        try (SocketChannel socketChannel = SocketChannel.open()) {
            //设置非阻塞模式
            socketChannel.configureBlocking(false);
            InetAddress inetAddress = InetAddress.getByName(SERVER_IP);
            InetSocketAddress socketAddress = new InetSocketAddress(inetAddress, PORT);
            //由于该套接字是非阻塞的，因此对connect()方法的调用可能会在连接建立之前返回
            //如果在返回之前已经成功建立了连接则返回true，否则返回false
            //我们通过持续调用finishConnect()方法来轮询连接的状态，这种忙等的方式非常消耗资源，这里只是为了演示
            if (!socketChannel.connect(socketAddress)) {
                while (!socketChannel.finishConnect()) {
                    System.out.print(".");
                }
            }
            String content = "hello! I am ruby!";
            byte[] bytes = content.getBytes(StandardCharsets.UTF_8.name());
            //发送数据的缓冲区
            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
            //接收数据的缓冲区
            ByteBuffer receiveBuf = ByteBuffer.allocate(bytes.length);
            int receiveLen = 0;
            int totalSize = 0;
            while (totalSize < bytes.length) {
                //发送数据
                if (byteBuffer.hasRemaining()) {
                    socketChannel.write(byteBuffer);
                }
                //读取数据
                if ((receiveLen = socketChannel.read(receiveBuf)) == -1) {
                    throw new SocketException("Connection closed prematurely");
                }
                totalSize += receiveLen;
                System.out.print(".");
            }
            System.out.println("Received: " + new String(receiveBuf.array(), 0, totalSize));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

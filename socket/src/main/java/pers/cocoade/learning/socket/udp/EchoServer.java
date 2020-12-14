package pers.cocoade.learning.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.*;

public class EchoServer {
//    private static final int THREAD_NUMBER = Runtime.getRuntime().availableProcessors() * 2;
//    private static final Executor threadPool = new ThreadPoolExecutor(THREAD_NUMBER,THREAD_NUMBER,10, TimeUnit.SECONDS,
//            new LinkedBlockingDeque<>(1000), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());

    //
    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(9000);
            DatagramPacket packet = new DatagramPacket(new byte[65535],65535);
            while(true){
                serverSocket.receive(packet);
                System.out.println("Handling client at " + packet.getAddress().getHostAddress() + " on port " +
                        packet.getPort());
                int len = packet.getLength();
                System.out.println("data length: " + len);
                serverSocket.send(packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

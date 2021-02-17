package pers.cocoade.learning.socket.base.demo.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class EchoClient {
    private final static int TIMEOUT = 3000;

    private final static int MAX_TRIES = 5;


    public static void main(String[] args) {
        DatagramSocket socket= null;
        try {
            InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
            socket = new DatagramSocket();
            //数据报文套接字的超时时间，用来控制调用receive()方法的最长阻塞时间（毫秒）
            socket.setSoTimeout(TIMEOUT);
            //发送报文
            byte[] content = "hello Weiss! I am Ruby!!!!!!!!!!".getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(content,content.length,serverAddress,9000);
            //创建接收的数据报文，数据包报文一定不能比接收的数据小,否则会丢死部分数据
            //一个DatagramPacket实例中所运行传输的最大的数据量为65507
            //IP协议头总长度是4个字节，因此数据（包括IP头部）最大是65535个字节。
            DatagramPacket receivePacket = new DatagramPacket(new byte[65535],65535);
            //由于UDP是不可靠的传输，如果数据报文丢失我们需要重新发送
            //我们通过阻塞接收等待，如果3秒后没回应，则重发，直到重复次数超过MAX_TRIES
            boolean receivedResponse = false;
            int tries = 0;
            do{
                //发送
                socket.send(datagramPacket);
                try {
                    //阻塞接收
                    socket.receive(receivePacket);
                    if(!receivePacket.getAddress().equals(serverAddress)){
                        throw new IOException("Received packet from an unknown source");
                    }
                    receivedResponse = true;
                }catch (Exception e){
                    e.printStackTrace();
                    tries++;
                    System.out.println("Timed out," + (MAX_TRIES - tries) + " more tries...");
                }
            }while((!receivedResponse) && (tries < MAX_TRIES));
            if(receivedResponse){
                byte[] buffer = Arrays.copyOfRange(receivePacket.getData(),receivePacket.getOffset(),
                        receivePacket.getOffset() + receivePacket.getLength());
                System.out.println("received: "  + new String(buffer));
            }else{
                System.out.println("No response -- giving up.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(socket != null){
                socket.close();
            }
        }
    }
}

package pers.cocoadel.learning.socket.echo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.ArrayList;
import java.util.Iterator;

public class TCPServerSelector {
    private static final int TIME_OUT = 3000;

    private static final int BUF_SIZE = 256;

    public static void main(String[] args) {
        TCPServerSelector serverSelector = new TCPServerSelector();
        serverSelector.start(new int[]{8080});
        ArrayList<Integer> arrayList = new ArrayList<>();
        int[] ints = arrayList.stream().mapToInt(Integer::intValue).toArray();
    }

    private void start(int[] ports) {
        try {
            //创建Selector
            Selector selector = Selector.open();
            //创建相关端口的ServerSocketChannel监听,并且注册到Selector
            for (int port : ports) {
                ServerSocketChannel serverChannel = ServerSocketChannel.open();
                serverChannel.configureBlocking(false);
                InetSocketAddress socketAddress = new InetSocketAddress(port);
                serverChannel.bind(socketAddress);
                serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            }

            //创建相关协议处理对象
            TCPProtocol tcpProtocol = new EchoServerSelectorProtocol(BUF_SIZE);
            //轮询Selector，处理相关I/O事件
            while (true) {
                if (selector.select(TIME_OUT) == 0) {
                    System.out.println(".");
                    continue;
                }

                Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
                while (keyIter.hasNext()) {
                    SelectionKey key = keyIter.next();
                    try {
                        if (key.isAcceptable()) {
                            tcpProtocol.handleAccept(key);
                        }

                        if (key.isReadable()) {
                            tcpProtocol.handleRead(key);
                        }

                        if (key.isWritable()) {
                            tcpProtocol.handleWrite(key);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        key.cancel();
                        if(key.channel() != null){
                            key.channel().close();
                        }
                    }finally {
                        keyIter.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

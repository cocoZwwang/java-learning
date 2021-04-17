package pers.cocoadel.leanring.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer implements Runnable{
    private Selector selector;

    private ServerSocketChannel serverChannel;

    private volatile boolean stop;

    public MultiplexerTimeServer(int port) {
        try {
            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(port),1024);
            //accept 注册事件 代替了 acceptor
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The time server is start in port: " + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void stop(){
        this.stop = true;
    }


    public void run() {
        while(!stop){
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                //已经就绪的事件
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while(it.hasNext()){
                    key = it.next();
                    it.remove();
                    try {
                        //处理
                        handleInput(key);
                    }catch (Exception e){
                        key.cancel();
                        if(key.channel() != null){
                            key.channel().close();
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if(selector != null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if(key.isValid()){
            //处理新接入的请求消息
            if(key.isAcceptable()){
                //Accept the new connection
                ServerSocketChannel scc = (ServerSocketChannel) key.channel();
                SocketChannel sc = scc.accept();
                //设置为非阻塞
                sc.configureBlocking(false);
                sc.register(selector,SelectionKey.OP_READ);
            }

            if(key.isReadable()){
                //Read data
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if(readBytes > 0){
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("The time server receive order: " + body);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ?
                            new Date(System.currentTimeMillis()).toString() :
                            "BAD ORDER";
                    doWrite(sc,currentTime);
                }else if (readBytes < 0){
                    //对端链路关闭
                    key.cancel();
                    sc.close();
                }

                //0字节忽略
            }
        }
    }

    private void doWrite(SocketChannel sc, String res) throws IOException {
        if(res != null && res.trim().length() > 0){
            byte[] bytes = res.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            sc.write(writeBuffer);
        }
    }
}

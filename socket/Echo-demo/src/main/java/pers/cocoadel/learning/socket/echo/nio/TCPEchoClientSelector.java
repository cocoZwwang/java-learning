package pers.cocoadel.learning.socket.echo.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class TCPEchoClientSelector {
    private final static int PORT = 8080;

    private final static String SERVER_IP = "localhost";

    private static final int TIME_OUT = 3000;

    private volatile boolean stop = false;

    public static void main(String[] args) {
        TCPEchoClientSelector client = new TCPEchoClientSelector();
        client.start();
    }

    private void start() {
        try (SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.configureBlocking(false);
            InetAddress inetAddress = InetAddress.getByName(SERVER_IP);
            InetSocketAddress socketAddress = new InetSocketAddress(inetAddress, PORT);

            String content = "hello! I am ruby!";
            byte[] bytes = content.getBytes(StandardCharsets.UTF_8.name());
            //使用selector来代替忙等
            ByteBuffer receiveBuf = ByteBuffer.wrap(bytes);
            //通过静态方法open()构造一个Selector实例
            Selector selector = Selector.open();
            //连接远程TCP服务
            //由于是非阻塞的SocketChannel，因此connect()方法会立马返回。
            //所以此时连接有可能已经创建成功，有可能还在创建中
            //如果返回true：表示创建成功，false：表示创建中
            if (socketChannel.connect(socketAddress)) {
                //如果创建成功则注册可读、可写事件到selector实例上。
                socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, receiveBuf);
            } else {
                //如果还在创建过程中，则注册可连接到selector实例上
                socketChannel.register(selector, SelectionKey.OP_CONNECT, receiveBuf);
            }
            //遍历所有已就绪的selectionKey，监听并且处理响应时间
            doScanSelectionKeys(selector);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //遍历所有已就绪的selectionKey
    private void doScanSelectionKeys(Selector selector) throws IOException {
        while (!stop) {
            if (selector.select(TIME_OUT) == 0) {
                System.out.print(".");
                continue;
            }
            Iterator<SelectionKey> keyItor = selector.selectedKeys().iterator();
            while (keyItor.hasNext()) {
                SelectionKey key = keyItor.next();
                try {
                    if (key.isConnectable())//可查看连接结果
                        handleConnect(key);
                    if(key.isWritable())//可写
                        handleWrite(key);
                    if(key.isReadable())//可读
                        handleRead(key);
                } catch (IOException e) {
                    //异常关闭通道
                    doCloseSelectionKey(key);
                } finally {
                    keyItor.remove();
                }
            }
        }
    }

    //关闭通道
    private void doCloseSelectionKey(SelectionKey selectionKey) throws IOException {
        if (selectionKey != null) {
            selectionKey.cancel();
            if (selectionKey.channel() != null) {
                selectionKey.channel().close();
            }
        }
    }

    private void handleConnect(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        //connectable事件表示和对端有连接的结果，但是可能是成功也可能是失败
        //所以这里需要调用socketChannel.finishConnect()检查连接是否成功。
        //如果成功返回true，如果失败会抛出IOException
        if (socketChannel.finishConnect()) {
            selectionKey.interestOps(SelectionKey.OP_WRITE | SelectionKey.OP_READ);
        }
        System.out.println("handleConnect!");
    }

    private void handleRead(SelectionKey selectionKey) throws IOException {
        System.out.println("handleRead!");
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
        byteBuffer.clear();
        channel.read(byteBuffer);
        byte[] bytes = byteBuffer.array();
        doCloseSelectionKey(selectionKey);
        System.out.println("receive msg from server: " + new String(bytes));
        selectionKey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
    }

    private void handleWrite(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
        socketChannel.write(byteBuffer);
        if (!byteBuffer.hasRemaining()) {
            //如果缓冲区中之前接收的数据已经没有剩余，则标记为只读，不在写数据
            selectionKey.interestOps(SelectionKey.OP_READ);
        }
    }
}

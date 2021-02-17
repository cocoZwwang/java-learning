package pers.cocoadel.learning.socket.echo.nio;

import sun.security.util.Length;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class EchoServerSelectorProtocol implements TCPProtocol {

    private final int bufSize;

    public EchoServerSelectorProtocol(int bufSize) {
        this.bufSize = bufSize;
    }

    @Override
    public void handleAccept(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel clientSocket = serverSocketChannel.accept();
        clientSocket.configureBlocking(false);
        ByteBuffer byteBuffer = ByteBuffer.allocate(bufSize);
        clientSocket.register(selectionKey.selector(), SelectionKey.OP_READ, byteBuffer);
        System.out.println("handleAccept");
    }

    @Override
    public void handleRead(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
        long bytesRead = socketChannel.read(byteBuffer);
        //如果返回-1表示底层连接已经关闭，此时需要关闭通道
        if (bytesRead == -1) {
            socketChannel.close();
        } else if (bytesRead > 0) {
            selectionKey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        }
        System.out.println("handleRead");
    }

    @Override
    public void handleWrite(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
        byteBuffer.flip();
        if(byteBuffer.hasRemaining()){
            socketChannel.write(byteBuffer);
        }else{
            //如果缓冲区中之前接收的数据已经没有剩余，则标记为只读，不在写数据
            selectionKey.interestOps(SelectionKey.OP_READ);
        }
        //压缩缓冲区
        //如果缓冲区 中还有剩余的数据，则改操作将其移动到缓冲区的前端，以使下次迭代能够读入更多数据。
        //在任何情况下，该操作都将重置缓冲区的状态，因此缓冲区又变为可读的。
        byteBuffer.compact();
        System.out.println("handleWrite");
    }
}

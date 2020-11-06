package pers.cocoadel.leanring.nio.buffer;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * NIO支持通过Buffer数组来进行读写数据
 */
public class BufferScatteringAngGatheringDemo {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8808));

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(3);
        byteBuffers[1] = ByteBuffer.allocate(5);

        int maxLen = 8;

        SocketChannel socketChannel = serverSocketChannel.accept();
        int read = 0;
        while(read < maxLen && socketChannel.isOpen()){
            read += socketChannel.read(byteBuffers);
            for(ByteBuffer b : byteBuffers){
                System.out.printf("position:%s, limit:%s\n",b.position(),b.limit());
            }
        }

        for(ByteBuffer b : byteBuffers){
            b.flip();
            System.out.printf("String:%s\n",new String(b.array()));
        }

        int write = 0;
        while(write < maxLen){
            write += socketChannel.write(byteBuffers);
        }

        for(ByteBuffer b : byteBuffers){
            b.clear();
        }
    }
}

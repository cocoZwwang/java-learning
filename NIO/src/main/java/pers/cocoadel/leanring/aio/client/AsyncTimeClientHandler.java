package pers.cocoadel.leanring.aio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

public class AsyncTimeClientHandler implements Runnable,
        CompletionHandler<Void,AsyncTimeClientHandler> {
    private AsynchronousSocketChannel client;
    private String ip;
    private int port;
    private CountDownLatch latch;

    public AsyncTimeClientHandler(String ip, int port) {
        this.ip = ip;
        this.port = port;
        try {
            client = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        client.connect(new InetSocketAddress(ip, port),this,this);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void completed(Void result, AsyncTimeClientHandler attachment) {
        byte[] bytes = "QUERY TIME ORDER".getBytes(StandardCharsets.UTF_8);
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        attachment.client.write(writeBuffer,writeBuffer,new WriteCompletionHandler(attachment));
        System.out.println("send req to port: " + port);
    }

    @Override
    public void failed(Throwable exc, AsyncTimeClientHandler attachment) {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class WriteCompletionHandler implements
            CompletionHandler<Integer,ByteBuffer>{

        private final AsyncTimeClientHandler clientHandler;

        public WriteCompletionHandler(AsyncTimeClientHandler clientHandler) {
            this.clientHandler = clientHandler;
        }

        @Override
        public void completed(Integer result, ByteBuffer attachment) {
            //如果没有写完，继续写
            if (attachment.hasRemaining()) {
                clientHandler.client.write(attachment, attachment, this);
                return;
            }

            //如果写完，则读
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            clientHandler.client.read(readBuffer, readBuffer, new ReadCompletionHandler(clientHandler));
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
            try {
                clientHandler.client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ReadCompletionHandler  implements
            CompletionHandler<Integer,ByteBuffer>{
        private final AsyncTimeClientHandler clientHandler;

        public ReadCompletionHandler(AsyncTimeClientHandler clientHandler) {
            this.clientHandler = clientHandler;
        }

        @Override
        public void completed(Integer result, ByteBuffer attachment) {
            attachment.flip();
            byte[] bytes = new byte[attachment.remaining()];
            attachment.get(bytes);
            String body  = new String(bytes, StandardCharsets.UTF_8);
            System.out.println("Now is : " + body);
            clientHandler.latch.countDown();
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
            try {
                clientHandler.client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

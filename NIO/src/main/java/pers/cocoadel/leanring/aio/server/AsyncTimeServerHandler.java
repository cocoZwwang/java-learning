package pers.cocoadel.leanring.aio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

public class AsyncTimeServerHandler implements Runnable{
    CountDownLatch latch;
    AsynchronousServerSocketChannel serverSocketChannel;

    public AsyncTimeServerHandler(int port) {
        try {
            serverSocketChannel = AsynchronousServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("The Time server is start in port: " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        latch = new CountDownLatch(1);

        doAccept();
        try {
            //因为doAccept 是异步 所以这里需要使用 CountDownLatch await，否则线程会直接结束掉。
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    private void doAccept() {
        serverSocketChannel.accept(this,new AcceptCompletionHandler());
    }
}

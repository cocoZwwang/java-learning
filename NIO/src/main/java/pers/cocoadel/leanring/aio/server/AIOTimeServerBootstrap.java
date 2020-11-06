package pers.cocoadel.leanring.aio.server;

public class AIOTimeServerBootstrap {
    public static void main(String[] args) {
        AsyncTimeServerHandler serverHandler = new AsyncTimeServerHandler(8808);
        new Thread(serverHandler,"AIO-AsyncTimeServerHandler-001").start();
    }
}

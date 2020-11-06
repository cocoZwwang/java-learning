package pers.cocoadel.leanring.aio.client;

public class AsyncTimeClientBootstrap {
    public static void main(String[] args) {
        AsyncTimeClientHandler clientHandler = new AsyncTimeClientHandler("127.0.0.1",8808);
        new Thread(clientHandler,"AIO-AsyncTimeClientHandler-001").start();
    }
}

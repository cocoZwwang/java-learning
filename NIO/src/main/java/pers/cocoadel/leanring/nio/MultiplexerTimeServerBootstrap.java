package pers.cocoadel.leanring.nio;

public class MultiplexerTimeServerBootstrap {
    public static void main(String[] args) {
        new Thread(new MultiplexerTimeServer(8080)).start();
    }
}

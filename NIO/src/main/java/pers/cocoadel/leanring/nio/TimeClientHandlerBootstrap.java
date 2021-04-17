package pers.cocoadel.leanring.nio;

public class TimeClientHandlerBootstrap {

    public static void main(String[] args) {
        new Thread(new TimeClientHandler("localhost",8080)).start();
    }
}

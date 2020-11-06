package pers.cocoadel.learning.netty5.server.timeserver;

public class TimeServerBootstrap {

    public static void main(String[] args) {
        TimeServer timeServer = new TimeServer();
        timeServer.bind(8808);
    }
}

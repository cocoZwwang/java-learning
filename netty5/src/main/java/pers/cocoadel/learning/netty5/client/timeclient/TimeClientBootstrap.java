package pers.cocoadel.learning.netty5.client.timeclient;

public class TimeClientBootstrap {
    public static void main(String[] args) {
        TimeClient timeClient = new TimeClient();
        timeClient.connect("127.0.0.1",8808);
    }
}

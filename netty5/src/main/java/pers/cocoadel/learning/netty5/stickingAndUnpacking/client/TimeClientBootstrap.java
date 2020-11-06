package pers.cocoadel.learning.netty5.stickingAndUnpacking.client;

import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import pers.cocoadel.learning.netty5.client.timeclient.TimeClient;

public class TimeClientBootstrap {
    public static void main(String[] args) {
        TimeClient timeclient = new TimeClient();
//        timeclient.connect("127.0.0.1", 8808, new TimeClientHandler());
        //利用LineBasedFrameDecoder解决Tcp粘包和拆包的问题
        //利用StringDecoder来对字节数组进行转换成String
        timeclient.connect("127.0.0.1", 8808, new LineBasedFrameDecoder(1024),
                new StringDecoder(),new LineBaseStringTimeClientHandler());
    }
}

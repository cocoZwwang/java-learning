package pers.cocoadel.learning.netty5.stickingAndUnpacking.linebase.sever;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import pers.cocoadel.learning.netty5.server.timeserver.TimeServer;

/**
 * 演示Tcp的粘包和拆包问题
 */
public class TimeServerBootstrap {
    public static void main(String[] args) {
        TimeServer timeServer = new TimeServer();
        timeServer.bind(8808, new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                //利用LineBasedFrameDecoder解决粘包和拆包的问题
                //利用StringDecoder对字节数组进行转换成String
                socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                socketChannel.pipeline().addLast(new StringDecoder());
                socketChannel.pipeline().addLast(new LineBaseStringTimeServerHandler());
//                socketChannel.pipeline().addLast(new TimeServerHandler());
            }
        });


    }
}

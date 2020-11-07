package pers.cocoadel.learning.netty5.stickingAndUnpacking.delimiter.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;
import pers.cocoadel.learning.netty5.server.echoserver.EchoServer;


public class EchoServerBootstrap {
    public static void main(String[] args) {
        EchoServer echoServer = new EchoServer();
        //使用自定义分割符解决粘包/拆包问题
        //使用String编码器实现字节数组和String的转换
        ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes(CharsetUtil.UTF_8));
        ChannelInitializer<SocketChannel> channelInitializer = new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new DelimiterDecoderEchoServerHandler());
            }
        };
        echoServer.bind(8801, channelInitializer);

        //使用定长的解码器解决粘包/拆包问题
//        echoServer.bind(8801, new FixedLengthFrameDecoder(20),
//                new StringDecoder(), new DelimiterDecoderEchoServerHandler());
    }
}

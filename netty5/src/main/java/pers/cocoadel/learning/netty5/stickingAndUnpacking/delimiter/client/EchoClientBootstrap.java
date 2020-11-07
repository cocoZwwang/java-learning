package pers.cocoadel.learning.netty5.stickingAndUnpacking.delimiter.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;
import pers.cocoadel.learning.netty5.client.echoclient.EchoClient;

public class EchoClientBootstrap {

    public static void main(String[] args) {
        EchoClient echoClient = new EchoClient();
        //使用自定义分隔符解决粘包和拆包的问题
        //使用String编码器实现字节数组和String的转换
        ByteBuf byteBuf = Unpooled.copiedBuffer("$_".getBytes(CharsetUtil.UTF_8));
        echoClient.connect("localhost", 8801, new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, byteBuf));
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new DelimiterDecoderEchoClientHandler());
            }
        });

    }
}

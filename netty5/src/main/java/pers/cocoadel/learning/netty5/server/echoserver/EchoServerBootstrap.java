package pers.cocoadel.learning.netty5.server.echoserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;


public class EchoServerBootstrap {
    public static void main(String[] args) {
        EchoServer echoServer = new EchoServer();
        //使用自定义分割符解决粘包/拆包问题
        //使用String编码器实现字节数组和String的转换
        ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes(CharsetUtil.UTF_8));
        echoServer.bind(8801, new DelimiterBasedFrameDecoder(1024, delimiter),
                new StringDecoder(), new DelimiterDecoderEchoServerHandler());

        //使用定长的解码器解决粘包/拆包问题
//        echoServer.bind(8801, new FixedLengthFrameDecoder(20),
//                new StringDecoder(), new DelimiterDecoderEchoServerHandler());
    }
}

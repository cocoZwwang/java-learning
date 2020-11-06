package pers.cocoadel.learning.netty5.client.echoclient;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

public class EchoClientBootstrap {

    public static void main(String[] args) {
        EchoClient echoClient = new EchoClient();
        //使用自定义分隔符解决粘包和拆包的问题
        //使用String编码器实现字节数组和String的转换
        ByteBuf byteBuf = Unpooled.copiedBuffer("$_".getBytes(CharsetUtil.UTF_8));
        echoClient.connect("localhost", 8801, new DelimiterBasedFrameDecoder(1024, byteBuf),
                new StringDecoder(), new DelimiterDecoderEchoClientHandler());

    }
}

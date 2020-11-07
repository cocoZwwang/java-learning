package pers.cocoadel.learning.netty5.stickingAndUnpacking.delimiter.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

/**
 * EchoServer的
 * 基于{@link io.netty.handler.codec.DelimiterBasedFrameDecoder} 和
 * {@link io.netty.handler.codec.string.StringDecoder}的消息处理
 */
public class DelimiterDecoderEchoServerHandler extends ChannelHandlerAdapter {
    private int count;

    private static final String delimiter = "$_";

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String req = (String) msg;
        System.out.printf("This is %s times receive the client: [%s]\n",++count,req);
        String rsp = req + delimiter;
        ByteBuf byteBuf = Unpooled.copiedBuffer(rsp.getBytes(CharsetUtil.UTF_8));
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}

package pers.cocoadel.learning.netty5.client.echoclient;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

public class DelimiterDecoderEchoClientHandler extends ChannelHandlerAdapter {

    private int count = 0;

    private static final String delimiter = "$_";

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String req = "Hello CocoAdel! Welcome to Netty.";
        req += delimiter;
        for (int i = 0; i < 10; i++) {
            ByteBuf byteBuf = Unpooled.copiedBuffer(req.getBytes(CharsetUtil.UTF_8));
            ctx.writeAndFlush(byteBuf);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String rsp = (String) msg;
        System.out.printf("This is %s times receive the server:[%s]\n", ++count, rsp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}

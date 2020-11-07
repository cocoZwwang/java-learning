package pers.cocoadel.learning.netty5.stickingAndUnpacking.linebase.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

public class TimeClientHandler extends ChannelHandlerAdapter{
    private int counts = 0;
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String req = "QUERY TIME ORDER"+ System.lineSeparator();
        for (int i = 0; i < 10; i++) {
            byte[] bytes = req.getBytes(CharsetUtil.UTF_8);
            ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);
            ctx.writeAndFlush(byteBuf);
        }
    }

    /**
     *这里很可能也会发送粘包/拆包行为，counts的次数很可能不会等于请求的次数
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf bytebuf = (ByteBuf) msg;
        byte[] bytes = new byte[bytebuf.readableBytes()];
        bytebuf.readBytes(bytes);
        String rsp = new String(bytes, CharsetUtil.UTF_8);
        rsp = rsp.substring(0,rsp.length() - System.lineSeparator().length());
        System.out.printf("[counts: %s]Now is %s\n",++counts,rsp);
    }
}

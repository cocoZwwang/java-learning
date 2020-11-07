package pers.cocoadel.learning.netty5.stickingAndUnpacking.linebase.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

public class LineBaseStringTimeClientHandler extends ChannelHandlerAdapter {
    private int counts = 0;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //记得要加分隔符，否则LineBasedFrameDecoder无法根据换行符进行分割
        String req = "QUERY TIME ORDER" + System.lineSeparator();
        for(int i = 0; i < 10; i++){
            ByteBuf byteBuf = Unpooled.copiedBuffer(req.getBytes(CharsetUtil.UTF_8));
            ctx.writeAndFlush(byteBuf);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String rsp = (String) msg;
        System.out.printf("[count:%s] Now is %s\n", ++counts, rsp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}

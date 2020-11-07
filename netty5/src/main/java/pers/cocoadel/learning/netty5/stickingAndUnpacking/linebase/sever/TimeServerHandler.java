package pers.cocoadel.learning.netty5.stickingAndUnpacking.linebase.sever;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

import java.util.Date;


/**
 * 演示TCP粘包和拆包的问题
 */
public class TimeServerHandler extends ChannelHandlerAdapter {
    private int counts = 0;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     *每次接收到一条命令的时候，计数+1
     * 如果没有发生Tcp粘包/拆包问题的话，则会打印10次请求，并且每次请求计数会递增1
     * 但是这里很可能会发送粘包或者拆包，就是count很可能不会等于10
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        //这里客户端发送的消息后面会带上分隔符，但是因为Tcp粘包和拆包的问题，导致不会起作用。
        String req = new String(bytes, CharsetUtil.UTF_8);
        String body = req.substring(0,req.length() - System.lineSeparator().length());
        System.out.printf("【count: %s】接收到消息:%s\n", ++counts, body);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ?
               new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
        currentTime = currentTime + System.lineSeparator();
        bytes = currentTime.getBytes(CharsetUtil.UTF_8);
        byteBuf = Unpooled.copiedBuffer(bytes);
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}

package pers.cocoadel.learning.netty5.custom.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import pers.cocoadel.learning.netty5.custom.domain.NettyMessage;

/**
 * 服务端业务处理
 */
public class ServerBusinessHandler extends SimpleChannelInboundHandler<NettyMessage> {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
        System.out.println("this is received from client: " + msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}

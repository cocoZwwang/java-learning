package pers.cocoadel.learning.netty5.custom.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import pers.cocoadel.learning.netty5.custom.domain.NettyMessage;
import pers.cocoadel.learning.netty5.custom.domain.NettyMessageType;

/**
 * 登录请求
 */
public class LoginAuthReqHandler extends SimpleChannelInboundHandler<NettyMessage> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        NettyMessage nettyMessage = NettyMessage.createNettyMessage();
        NettyMessage.Header header = nettyMessage.getHeader();
        header.setType((byte) NettyMessageType.LOGIN_REQ.code);
        ctx.writeAndFlush(nettyMessage);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
        ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}

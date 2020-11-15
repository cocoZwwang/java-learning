package pers.cocoadel.learning.netty5.custom.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import pers.cocoadel.learning.netty5.custom.domain.NettyMessage;
import pers.cocoadel.learning.netty5.custom.domain.NettyMessageType;

public class HeartBeatRspHandler extends SimpleChannelInboundHandler<NettyMessage> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
        NettyMessage.Header header = msg.getHeader();
        if (header != null && header.getType() == NettyMessageType.HEART_BEAT_RSP.code) {
            System.out.println("the heart beat rsp from server: " + msg);
        }else{
            ctx.fireChannelRead(msg);
        }
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

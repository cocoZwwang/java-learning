package pers.cocoadel.learning.netty5.custom.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import pers.cocoadel.learning.netty5.custom.domain.NettyMessage;
import pers.cocoadel.learning.netty5.custom.domain.NettyMessageType;

/**
 * 心跳返回处理
 */
public class HearBeatRspHandler extends SimpleChannelInboundHandler<NettyMessage> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
        NettyMessage.Header header = msg.getHeader();
        //如果是心跳请求
        if (header != null && header.getType() == NettyMessageType.HEART_BEAT_REQ.code) {
            System.out.println("the heart beat received from client: " + msg);
            NettyMessage rspMsg = NettyMessage.createNettyMessage();
            rspMsg.getHeader().setType((byte) NettyMessageType.HEART_BEAT_RSP.code);
            ctx.writeAndFlush(rspMsg);
        }else{
            ctx.fireChannelRead(msg);
        }
    }
}

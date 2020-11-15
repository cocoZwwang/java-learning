package pers.cocoadel.learning.netty5.custom.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import pers.cocoadel.learning.netty5.custom.domain.NettyMessage;
import pers.cocoadel.learning.netty5.custom.domain.NettyMessageType;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 心跳请求处理
 */
public class HearBeatReqHandler extends SimpleChannelInboundHandler<NettyMessage> {
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
        NettyMessage.Header header = msg.getHeader();
        if (header != null && header.getType() == NettyMessageType.LOGIN_RSP.code) {
            String  res = msg.getBody().toString();
            if ("0".equals(res)) {
                System.out.println("请求握手成功！");
                //请求握手成功
                startHeartBeatTask(ctx);
            }else{
                //请求握手失败！
                System.out.println("请求握手失败！");
                ctx.close();
            }
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

    private void startHeartBeatTask(ChannelHandlerContext ctx) {
        executor.scheduleAtFixedRate(new Task(ctx), 1, 5, TimeUnit.SECONDS);
    }

    private static class Task implements Runnable{
        private final ChannelHandlerContext ctx;

        private Task(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            NettyMessage nettyMessage = NettyMessage.createNettyMessage();
            NettyMessage.Header header = nettyMessage.getHeader();
            header.setType((byte) NettyMessageType.HEART_BEAT_REQ.code);
            ctx.writeAndFlush(nettyMessage);
        }
    }
}

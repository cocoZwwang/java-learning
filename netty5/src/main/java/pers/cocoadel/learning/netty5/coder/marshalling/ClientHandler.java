package pers.cocoadel.learning.netty5.coder.marshalling;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ClientHandler extends ChannelHandlerAdapter {

    private static final int sendTimes = 100;
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < sendTimes; i++) {
            SubscribeReq subscribeReq = new SubscribeReq();
            subscribeReq.setSubReqID(i);
            subscribeReq.setUserName("ruby");
            subscribeReq.setAddress("GuangZhou");
            subscribeReq.setProductName("Netty fot Marshalling");
            ctx.writeAndFlush(subscribeReq);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("this is the message accepted from server:" + msg.toString());

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}

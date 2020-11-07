package pers.cocoadel.learning.netty5.coder.marshalling;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("the is the message accepted from client:" + msg.toString());
        SubscribeReq subscribeReq = (SubscribeReq) msg;
        if ("ruby".equalsIgnoreCase(subscribeReq.getUserName())) {
            ctx.writeAndFlush(resp(subscribeReq.getSubReqID()));
        }
    }

    private SubscribeResp resp(int id){
        SubscribeResp resp = new SubscribeResp();
        resp.setSubReqID(id);
        resp.setRespCode(0);
        resp.setDesc("Netty book order succeed! the 3 days latter,send to the designated address");
        return resp;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}

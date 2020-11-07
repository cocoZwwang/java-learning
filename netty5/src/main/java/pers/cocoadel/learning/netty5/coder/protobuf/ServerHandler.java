package pers.cocoadel.learning.netty5.coder.protobuf;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("this is accept the message from client: " + msg);
        SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq) msg;
        if ("ruby".equalsIgnoreCase(req.getUserName())) {
            SubscribeResProtobuf.SubscribeResp resp = resp(req.getSubReqID());
            ctx.writeAndFlush(resp);
        }
    }

    private SubscribeResProtobuf.SubscribeResp resp(int subReqID) {
        SubscribeResProtobuf.SubscribeResp resp = SubscribeResProtobuf.SubscribeResp.newBuilder()
                .setSubReqID(subReqID)
                .setRespCode(0)
                .setDesc("Netty book order succeed,3 days latter,sent to the designated address")
                .build();
        return resp;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}

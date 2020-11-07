package pers.cocoadel.learning.netty5.coder.protobuf;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ClientHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i = 0; i < 10; i++){
            SubscribeReqProto.SubscribeReq req = SubscribeReqProto.SubscribeReq.newBuilder()
                    .setSubReqID(i)
                    .setUserName("ruby")
                    .setProductName("Netty for protobuf")
                    .setAddress("GuangZhou")
                    .build();
            ctx.write(req);
        }
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeResProtobuf.SubscribeResp resp = (SubscribeResProtobuf.SubscribeResp) msg;
        System.out.println("this is accept the message from server:" + resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}

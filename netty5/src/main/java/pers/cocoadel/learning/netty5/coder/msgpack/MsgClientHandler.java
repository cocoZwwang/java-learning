package pers.cocoadel.learning.netty5.coder.msgpack;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class MsgClientHandler extends ChannelHandlerAdapter {

    private static final int sendTimes = 10;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < sendTimes; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setName("ruby--"+ i);
            userInfo.setAge(i);
            ctx.writeAndFlush(userInfo);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.printf("this is receive the msg from server: [%s]\n", msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}

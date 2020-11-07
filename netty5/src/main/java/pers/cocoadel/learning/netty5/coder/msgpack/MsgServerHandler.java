package pers.cocoadel.learning.netty5.coder.msgpack;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.msgpack.MessagePack;
import org.msgpack.type.ArrayValue;


public class MsgServerHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ArrayValue arrayValue = (ArrayValue) msg;
        MessagePack messagePack = new MessagePack();
        UserInfo userInfo = messagePack.convert(arrayValue, UserInfo.class);
        System.out.printf("this is receive from client:[%s]\n", userInfo);
        ctx.writeAndFlush(userInfo);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}

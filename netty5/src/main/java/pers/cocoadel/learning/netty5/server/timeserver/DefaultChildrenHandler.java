package pers.cocoadel.learning.netty5.server.timeserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class DefaultChildrenHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel SocketChannel) throws Exception {
        SocketChannel.pipeline().addLast(new TimeServerHandler());
    }
}

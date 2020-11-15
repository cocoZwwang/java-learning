package pers.cocoadel.learning.netty5.custom.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import pers.cocoadel.learning.netty5.client.echoclient.EchoClient;
import pers.cocoadel.learning.netty5.custom.codec.NettyMessageDecoder;
import pers.cocoadel.learning.netty5.custom.codec.NettyMessageEncoder;
import pers.cocoadel.learning.netty5.custom.server.HearBeatRspHandler;

public class NettyClientBootstrap {
    public static void main(String[] args) {
        EchoClient echoClient = new EchoClient();
        echoClient.connect("localhost", 8810, new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4));
                ch.pipeline().addLast(new NettyMessageEncoder());
                ch.pipeline().addLast("timeOutHandler", new ReadTimeoutHandler(50));
                ch.pipeline().addLast(new LoginAuthReqHandler());
                ch.pipeline().addLast(new HearBeatReqHandler());
                ch.pipeline().addLast(new HearBeatRspHandler());
                ch.pipeline().addLast(new ClientBusinessHandler());
            }
        }, true);
    }
}

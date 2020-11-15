package pers.cocoadel.learning.netty5.custom.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import pers.cocoadel.learning.netty5.custom.codec.NettyMessageDecoder;
import pers.cocoadel.learning.netty5.custom.codec.NettyMessageEncoder;
import pers.cocoadel.learning.netty5.server.echoserver.EchoServer;

import java.util.logging.Level;

public class NettyServerBootstrap {

    public static void main(String[] args) {
        EchoServer echoServer = new EchoServer();
        echoServer.bind(8810, new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4));
                ch.pipeline().addLast(new NettyMessageEncoder());
                ch.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
                //登录处理
                ch.pipeline().addLast(new LoginAuthRspHandler());
                //心跳
                ch.pipeline().addLast(new HearBeatRspHandler());
                //服务端业务处理
                ch.pipeline().addLast(new ServerBusinessHandler());
            }
        });
    }
}

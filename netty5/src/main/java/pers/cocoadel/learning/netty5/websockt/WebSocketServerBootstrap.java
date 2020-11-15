package pers.cocoadel.learning.netty5.websockt;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import pers.cocoadel.learning.netty5.server.echoserver.EchoServer;

public class WebSocketServerBootstrap {
    public static void main(String[] args) {
        EchoServer echoServer = new EchoServer();
        echoServer.bind(8807, new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new HttpServerCodec());
                ch.pipeline().addLast(new HttpObjectAggregator(65535));
                ch.pipeline().addLast(new ChunkedWriteHandler());
                ch.pipeline().addLast(new WebSocketServerHandler());
            }
        });
    }
}

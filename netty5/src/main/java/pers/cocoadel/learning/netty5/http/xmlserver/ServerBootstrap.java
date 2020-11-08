package pers.cocoadel.learning.netty5.http.xmlserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import pers.cocoadel.learning.netty5.http.xmlserver.codec.HttpXmlRequestDecoder;
import pers.cocoadel.learning.netty5.http.xmlserver.codec.HttpXmlResponseEncoder;
import pers.cocoadel.learning.netty5.http.xmlserver.domain.Order;
import pers.cocoadel.learning.netty5.http.xmlserver.handler.ServerHandler;
import pers.cocoadel.learning.netty5.server.echoserver.EchoServer;

public class ServerBootstrap {

    public static void main(String[] args) {
        EchoServer echoServer = new EchoServer();
        echoServer.bind(8805, new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new HttpRequestDecoder());
                ch.pipeline().addLast(new HttpObjectAggregator(65536));
                ch.pipeline().addLast(new HttpXmlRequestDecoder(Order.class));
                ch.pipeline().addLast(new HttpResponseEncoder());
                ch.pipeline().addLast(new HttpXmlResponseEncoder());
                ch.pipeline().addLast(new ServerHandler());
            }
        });
    }
}

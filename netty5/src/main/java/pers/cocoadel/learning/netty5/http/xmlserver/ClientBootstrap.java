package pers.cocoadel.learning.netty5.http.xmlserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import pers.cocoadel.learning.netty5.client.echoclient.EchoClient;
import pers.cocoadel.learning.netty5.http.xmlserver.codec.HttpXmlRequestEncoder;
import pers.cocoadel.learning.netty5.http.xmlserver.codec.HttpXmlResponseDecoder;
import pers.cocoadel.learning.netty5.http.xmlserver.domain.Order;
import pers.cocoadel.learning.netty5.http.xmlserver.handler.ClientHandler;

public class ClientBootstrap {
    public static void main(String[] args) {
        EchoClient echoClient = new EchoClient();
        echoClient.connect("localhost", 8805, new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new HttpResponseDecoder());
                ch.pipeline().addLast(new HttpObjectAggregator(65536));
                ch.pipeline().addLast(new HttpXmlResponseDecoder(Order.class));
                ch.pipeline().addLast(new HttpRequestEncoder());
                ch.pipeline().addLast(new HttpXmlRequestEncoder());
                ch.pipeline().addLast(new ClientHandler());
            }
        });
    }
}

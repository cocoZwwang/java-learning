package pers.cocoadel.learning.netty5.coder.marshalling;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import pers.cocoadel.learning.netty5.client.echoclient.EchoClient;

public class ClientBootstrap {
    public static void main(String[] args) {
        EchoClient echoClient = new EchoClient();
        echoClient.connect("localhost", 8805, new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                //Marshalling的编解码器本身就已经有处理粘包/拆包的能力
                ch.pipeline().addLast(MarshallingCoderFactory.buildMarshallingDecoder());
                ch.pipeline().addLast(MarshallingCoderFactory.buildMarshallingEncoder());
                ch.pipeline().addLast(new ClientHandler());
            }
        });
    }
}

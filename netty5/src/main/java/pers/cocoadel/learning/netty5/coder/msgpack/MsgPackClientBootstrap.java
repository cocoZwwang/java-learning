package pers.cocoadel.learning.netty5.coder.msgpack;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import pers.cocoadel.learning.netty5.client.echoclient.EchoClient;

public class MsgPackClientBootstrap {
    public static void main(String[] args) {
        EchoClient echoClient = new EchoClient();
        echoClient.connect("127.0.0.1", 8802, new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65535, 0,
                        2, 0, 2));
                ch.pipeline().addLast("msgpack decoder",new MsgPackDecoder());
                ch.pipeline().addLast("frameEncoder",new LengthFieldPrepender(2));
                ch.pipeline().addLast("msgpack encoder", new MsgPackEncoder());
                ch.pipeline().addLast(new MsgClientHandler());
            }
        });
    }
}

package pers.cocoadel.learning.netty5.coder.msgpack;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import pers.cocoadel.learning.netty5.server.echoserver.EchoServer;

public class MsgPackServerBootstrap {

    public static void main(String[] args) {
//        MsgPackServer packServer = new MsgPackServer();
        EchoServer echoServer = new EchoServer();
        echoServer.bind(8802, new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                //添加半包处理
                //消息头使用2个字节表示消息的长度
                ch.pipeline().addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65535,
                        0, 2, 0, 2));
                ch.pipeline().addLast("msgpack decoder",new MsgPackDecoder());
                //添加序列化，自动添加消息头处理
                ch.pipeline().addLast("frameEncoder",new LengthFieldPrepender(2));
                ch.pipeline().addLast("msgpack encoder",new MsgPackEncoder());
                ch.pipeline().addLast(new MsgServerHandler());
            }
        });
    }
}

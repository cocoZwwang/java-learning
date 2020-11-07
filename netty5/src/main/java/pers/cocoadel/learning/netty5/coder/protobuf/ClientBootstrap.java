package pers.cocoadel.learning.netty5.coder.protobuf;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import pers.cocoadel.learning.netty5.client.echoclient.EchoClient;

/**
 * protobuf 客户端
 */
public class ClientBootstrap {

    public static void main(String[] args) {
        EchoClient echoClient = new EchoClient();
        echoClient.connect("localhost", 8804, new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                //对服务端返回的信息进行解码
                ch.pipeline().addLast(new ProtobufDecoder(SubscribeResProtobuf.SubscribeResp
                        .getDefaultInstance()));
                ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                ch.pipeline().addLast(new ProtobufEncoder());
                ch.pipeline().addLast(new ClientHandler());
            }
        });

    }
}

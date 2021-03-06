package pers.cocoadel.learning.netty5.client.timeclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;

public class TimeClient {
    public void connect(String ip, int port) {
        connect(ip,port,new TimeClientHandler());
    }

    public void connect(String ip, int port, ChannelHandler... channelHandlers) {

        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(channelHandlers);
                            socketChannel.config().setAllocator(UnpooledByteBufAllocator.DEFAULT);
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(ip,port).sync();
            channelFuture.channel().closeFuture()
                    .addListener((ChannelFutureListener) future -> workGroup.shutdownGracefully());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

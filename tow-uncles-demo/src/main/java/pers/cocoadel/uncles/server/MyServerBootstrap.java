package pers.cocoadel.uncles.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import pers.cocoadel.uncles.comm.codc.GreetHandlerDecoder;
import pers.cocoadel.uncles.comm.codc.GreetFrameDecoder;
import pers.cocoadel.uncles.comm.codc.GreetFrameEncoder;
import pers.cocoadel.uncles.comm.codc.GreetHandlerEncoder;

/**
 * https://time.geekbang.org/column/article/119988 课后作业
 */
public class MyServerBootstrap {

    public static void main(String[] args) {

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            EventLoopGroup work = new NioEventLoopGroup();
            EventLoopGroup boss = new NioEventLoopGroup(1);
            WriteBufferWaterMark waterMark = new WriteBufferWaterMark(32 * 1024,64 * 1024 * 1024 * 10);
            bootstrap.group(boss,work)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK,waterMark)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("GreetFrameDecoder", new GreetFrameDecoder());
                            pipeline.addLast("GreetFrameEncoder", new GreetFrameEncoder());

                            pipeline.addLast("GreetHandlerDecoder", new GreetHandlerDecoder());
                            pipeline.addLast("GreetHandlerEncoder", new GreetHandlerEncoder());

                            pipeline.addLast("ServerHandler",new ServerHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(8080).sync();
            channelFuture.channel().closeFuture().addListener((ChannelFutureListener) future -> {
                work.shutdownGracefully();
                boss.shutdownGracefully();
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

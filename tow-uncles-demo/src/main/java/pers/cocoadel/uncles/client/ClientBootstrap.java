package pers.cocoadel.uncles.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
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
public class ClientBootstrap {
    public static void main(String[] args) {
        try {
            final long startTime = System.currentTimeMillis();
            Bootstrap bootstrap = new Bootstrap();
            EventLoopGroup workGroup = new NioEventLoopGroup();
            WriteBufferWaterMark waterMark = new WriteBufferWaterMark(32 * 1024,1024 * 1024);
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.WRITE_BUFFER_WATER_MARK,waterMark)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("GreetFrameDecoder", new GreetFrameDecoder());
                            pipeline.addLast("GreetFrameEncoder", new GreetFrameEncoder());

                            pipeline.addLast("GreetHandlerDecoder", new GreetHandlerDecoder());
                            pipeline.addLast("GreetHandlerEncoder", new GreetHandlerEncoder());

                            pipeline.addLast("ClientHandler", new ClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect("localhost", 8080).sync();
            channelFuture
                    .channel()
                    .closeFuture()
                    .addListener((ChannelFutureListener) future -> {
                        System.out.println("总耗时：" + (System.currentTimeMillis() - startTime));
                        workGroup.shutdownGracefully();
                    });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

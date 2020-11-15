package pers.cocoadel.learning.netty5.client.echoclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.sql.Time;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EchoClient {
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public void connect(String ip, int port, ChannelInitializer<SocketChannel> channelInitializer) {
        connect(ip, port, channelInitializer, false);
    }

    public void connect(String ip, int port, ChannelInitializer<SocketChannel> channelInitializer, boolean reConnect) {
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(channelInitializer);
            ChannelFuture channelFuture = bootstrap.connect(ip,port).sync();
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            workGroup.shutdownGracefully();
            if(reConnect) {
                executor.execute(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    connect(ip, port, channelInitializer, true);
                });
            }
        }
    }
}

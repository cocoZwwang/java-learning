package pers.cocoadel.learning.netty4.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import pers.cocoadel.learning.netty4.client.codec.OrderFrameDecoder;
import pers.cocoadel.learning.netty4.client.codec.OrderFrameEncoder;
import pers.cocoadel.learning.netty4.client.codec.OrderProtocolDecoder;
import pers.cocoadel.learning.netty4.client.codec.OrderProtocolEncoder;
import pers.cocoadel.learning.netty4.client.dispatcher.RequestFuture;
import pers.cocoadel.learning.netty4.client.dispatcher.RequestPendingCenter;
import pers.cocoadel.learning.netty4.client.handler.ClientIdleCheckHandler;
import pers.cocoadel.learning.netty4.client.handler.KeepaliveHandler;
import pers.cocoadel.learning.netty4.common.RequestMessage;
import pers.cocoadel.learning.netty4.common.ResponseMessage;
import pers.cocoadel.learning.netty4.common.auth.AuthOperation;
import pers.cocoadel.learning.netty4.common.order.OrderOperation;
import pers.cocoadel.learning.netty4.util.IdUtil;


public class ClientV0 {
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        final EventLoopGroup workGroup = new NioEventLoopGroup();
        final RequestPendingCenter requestPendingCenter = new RequestPendingCenter();
        final KeepaliveHandler keepaliveHandler = new KeepaliveHandler();
        try {
            final SslContext sslContext = SslContextBuilder.forClient().build();
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                            ChannelPipeline pipeline = nioSocketChannel.pipeline();
                            //添加idle检测
                            pipeline.addLast("idleCheckHandler",new ClientIdleCheckHandler());
                            //添加SSL
                            final SslHandler sslHandler = sslContext.newHandler(nioSocketChannel.alloc());
                            pipeline.addLast("SslHandler", sslHandler);
                            //
                            pipeline.addLast("OrderFrameDecoder", new OrderFrameDecoder());
                            pipeline.addLast("OrderFrameEncoder",new OrderFrameEncoder());

                            pipeline.addLast("OrderProtocolDecoder",new OrderProtocolDecoder(requestPendingCenter));
                            pipeline.addLast("OrderProtocolEncoder",new OrderProtocolEncoder());

                            pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                            //添加KeepaliveHandler
                            pipeline.addLast("keepaliveHandler",keepaliveHandler);
                        }
                    });
            ChannelFuture future = bootstrap.connect("127.0.0.1",8090).sync();
//            ChannelFuture future = bootstrap.connect("192.168.3.100",8090).sync();
            future.channel().closeFuture().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    workGroup.shutdownGracefully();
                    System.out.println("client shutdown !");
                }
            });
            sendAuthRequest(future);
            RequestFuture requestFuture = sendOrderRequest(requestPendingCenter,future);
            ResponseMessage responseMessage = requestFuture.get();
            System.out.println(responseMessage.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void sendAuthRequest(ChannelFuture future) {
        RequestMessage requestMessage =  RequestMessage
                .createRequestMessage(IdUtil.next(), new AuthOperation("cocoAdel","123456"));
        future.channel().writeAndFlush(requestMessage);
    }

    private static RequestFuture sendOrderRequest(RequestPendingCenter requestPendingCenter,ChannelFuture future){
        RequestMessage requestMessage =  RequestMessage
                .createRequestMessage(IdUtil.next(), new OrderOperation(1001, "tudou"));
        RequestFuture requestFuture = new RequestFuture();
        requestPendingCenter.add(requestMessage.getMessageHeader().getStreamId(),requestFuture);
        future.channel().writeAndFlush(requestMessage);
        return requestFuture;
    }
}

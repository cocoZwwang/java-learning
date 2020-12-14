package pers.cocoadel.learning.netty4.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.flush.FlushConsolidationHandler;
import io.netty.handler.ipfilter.IpFilterRule;
import io.netty.handler.ipfilter.IpFilterRuleType;
import io.netty.handler.ipfilter.IpSubnetFilterRule;
import io.netty.handler.ipfilter.RuleBasedIpFilter;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.traffic.GlobalChannelTrafficShapingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.UnorderedThreadPoolEventExecutor;
import lombok.extern.slf4j.Slf4j;
import pers.cocoadel.learning.netty4.server.codec.OrderFrameDecoder;
import pers.cocoadel.learning.netty4.server.codec.OrderFrameEncoder;
import pers.cocoadel.learning.netty4.server.codec.OrderProtocolDecoder;
import pers.cocoadel.learning.netty4.server.codec.OrderProtocolEncoder;
import pers.cocoadel.learning.netty4.server.handler.AuthHandler;
import pers.cocoadel.learning.netty4.server.handler.MetricHandler;
import pers.cocoadel.learning.netty4.server.handler.OrderServerHandler;
import pers.cocoadel.learning.netty4.server.handler.ServerIdleCheckHandler;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

@Slf4j
public class Server {
    private final MetricHandler metricHandler = new MetricHandler();

    private final EventExecutorGroup eventExecutors = new UnorderedThreadPoolEventExecutor(10, new DefaultThreadFactory("business"));

    private final static long LIMIT = 100 * 1024 * 1024;

    private final GlobalChannelTrafficShapingHandler globalChannelTrafficShapingHandler =
            new GlobalChannelTrafficShapingHandler(new NioEventLoopGroup(), LIMIT, LIMIT, LIMIT, LIMIT);

    private final IpFilterRule ipFilterRule = new IpSubnetFilterRule("127.1.0.1",16, IpFilterRuleType.REJECT);

    private final RuleBasedIpFilter ruleBasedIpFilter = new RuleBasedIpFilter(ipFilterRule);

    private final AuthHandler authHandler = new AuthHandler();

    private SslContext sslContext;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start(){
        String os = getOs();
        if(os.startsWith("linux")){
            startWithEpoll();
        }else {
            startWithNio();
        }
    }

    public void startWithNio(){
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        final EventLoopGroup bossGroup = new NioEventLoopGroup(0, new DefaultThreadFactory("boss"));
        final EventLoopGroup workGroup = new NioEventLoopGroup(0, new DefaultThreadFactory("work"));
        try {
            initSslContext();
            serverBootstrap.group(bossGroup, workGroup);
            serverBootstrap.channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) {
                            setPipelineHandlers(ch,ch.pipeline());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(8090).sync();
            channelFuture.channel().closeFuture().addListener((ChannelFutureListener) channelFuture1 -> {
                bossGroup.shutdownGracefully();
                workGroup.shutdownGracefully();
                System.out.println("server shutdown !");
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("**********************start with nio***********************");
    }

    public void startWithEpoll(){
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        final EventLoopGroup bossGroup = new EpollEventLoopGroup(0, new DefaultThreadFactory("boss"));
        final EventLoopGroup workGroup = new EpollEventLoopGroup(0, new DefaultThreadFactory("work"));
        try {
            initSslContext();
            serverBootstrap.group(bossGroup, workGroup);
            serverBootstrap.channel(EpollServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<EpollSocketChannel>() {
                        @Override
                        protected void initChannel(EpollSocketChannel ch) {
                            setPipelineHandlers(ch,ch.pipeline());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(8090).sync();
            channelFuture.channel().closeFuture().addListener((ChannelFutureListener) channelFuture1 -> {
                bossGroup.shutdownGracefully();
                workGroup.shutdownGracefully();
                System.out.println("server shutdown !");
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("**********************start with epoll***********************");
    }

    private void initSslContext() throws CertificateException, SSLException {
        SelfSignedCertificate selfSignedCertificate = new SelfSignedCertificate();
        //打印生成证书的路径
        log.info(selfSignedCertificate.certificate().toString());
        sslContext = SslContextBuilder
                .forServer(selfSignedCertificate.certificate(),selfSignedCertificate.privateKey())
                .build();
    }

    private  void setPipelineHandlers(SocketChannel sh, ChannelPipeline pipeline){
        //添加IP地址黑名单
        pipeline.addLast("RuleBasedFilter",ruleBasedIpFilter);
        //流量整型
        pipeline.addLast("TSHandler", globalChannelTrafficShapingHandler);
        //idleCheck
        pipeline.addLast("idleCheckHandler", new ServerIdleCheckHandler());
        //添加SSl认证
        SslHandler sslHandler = sslContext.newHandler(sh.alloc());
        pipeline.addLast("SslHandler",sslHandler);
        //
        pipeline.addLast("OrderFrameDecoder", new OrderFrameDecoder());
        pipeline.addLast("OrderFrameEncoder", new OrderFrameEncoder());

        pipeline.addLast("OrderProtocolDecoder", new OrderProtocolDecoder());
        pipeline.addLast("OrderProtocolEncoder", new OrderProtocolEncoder());
        //添加认证
        pipeline.addLast("AuthHandler",authHandler);
        //度量
        pipeline.addLast("metricHandler", metricHandler);
        //延迟flush，提高吞吐量
        pipeline.addLast("flushConsolidationHandler", new FlushConsolidationHandler(5, true));
        //日志
        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
        //让业务线程在eventExecutors里面执行。
        pipeline.addLast(eventExecutors, "OrderServerHandler", new OrderServerHandler());
    }

    private String getOs(){
        return System.getProperty("os.name").toLowerCase();
    }
}

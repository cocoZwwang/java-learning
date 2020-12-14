package pers.cocoadel.learning.netty4.server.handler;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;
import io.netty.buffer.ByteBufAllocatorMetric;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocatorMetric;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 *  度量监控
 */
@ChannelHandler.Sharable
public class MetricHandler extends ChannelDuplexHandler {
    private final AtomicLong totalConnectionNumber = new AtomicLong();

    //非池化内存
    private final ByteBufAllocatorMetric byteBufAllocatorMetric = UnpooledByteBufAllocator.DEFAULT.metric();
    //池化内存
    private final PooledByteBufAllocatorMetric pooledByteBufAllocatorMetric = PooledByteBufAllocator.DEFAULT.metric();


    {
        MetricRegistry metricRegistry = new MetricRegistry();
        metricRegistry.register("totalConnectionNumber", (Gauge<Long>) totalConnectionNumber::get);
        //已经使用非池化直接内存
        metricRegistry.register("unPooledUsedDirectMemory",(Gauge<Long>) byteBufAllocatorMetric::usedDirectMemory);
        //已经使用的非池化堆内存
        metricRegistry.register("unPooledUsedHeapMemory",(Gauge<Long>) byteBufAllocatorMetric::usedHeapMemory);
        //已经使用的池化直接内存
        metricRegistry.register("pooledUsedDirectMemory",(Gauge<Long>) pooledByteBufAllocatorMetric::usedDirectMemory);
        //已经使用的池化堆内存
        metricRegistry.register("pooledUsedHeapMemory",(Gauge<Long>) pooledByteBufAllocatorMetric::usedHeapMemory);

        //控制台展示
        ConsoleReporter consoleReporter = ConsoleReporter.forRegistry(metricRegistry).build();
        //每5秒打印一次
        consoleReporter.start(5, TimeUnit.SECONDS);

        //Jmx展示
        JmxReporter jmxReporter = JmxReporter.forRegistry(metricRegistry).build();
        jmxReporter.start();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        totalConnectionNumber.incrementAndGet();
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        totalConnectionNumber.decrementAndGet();
        super.channelInactive(ctx);
    }
}

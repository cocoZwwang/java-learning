package pers.cocoadel.uncles.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import pers.cocoadel.uncles.comm.Greet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<Greet> {

    private final int total = 100000;

    private final Map<Long, Greet> map = new ConcurrentHashMap<>();

    private AtomicLong streamIdCreator = new AtomicLong(0);

    private static AtomicLong count = new AtomicLong();


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Greet msg) throws Exception {
        doChannelRead(ctx, msg);
    }

    private void doChannelRead(ChannelHandlerContext ctx, Greet response) {
        if (map.containsKey(response.getStreamId())) {
            if (Greet.rq0.equals(response.getMessage())) {
                ctx.write(greet(Greet.rs0));
                ctx.write(greet(Greet.rq1));
                ctx.flush();
            } else if (Greet.rs1.equals(response.getMessage())) {
                ctx.writeAndFlush(greet(Greet.rq2));
            } else if (Greet.rs2.equals(response.getMessage())) {
                if (count.incrementAndGet() == total) {
                    ctx.close();
                }
            }
        } else {
            log.error("不知道你说啥！");
        }
    }

    private Greet greet(String msg) {
        Greet greet = new Greet();
        greet.setMessage(msg);
        greet.setStreamId(streamIdCreator.incrementAndGet());
        map.put(greet.getStreamId(), greet);
        return greet;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < total; i++) {
            Greet meet = greet(Greet.meet);
            ctx.write(meet);
        }
        ctx.flush();
    }
}

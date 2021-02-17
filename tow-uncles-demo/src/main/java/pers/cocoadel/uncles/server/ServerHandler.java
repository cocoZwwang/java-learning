package pers.cocoadel.uncles.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import pers.cocoadel.uncles.comm.Greet;

public class ServerHandler extends SimpleChannelInboundHandler<Greet> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Greet msg) throws Exception {
//        System.out.println(msg);
        if (ctx.channel().isActive() && ctx.channel().isWritable()) {

            ctx.writeAndFlush(response(msg));
        }
    }

    private Greet response(Greet request) {
        Greet greet = new Greet();
        greet.setStreamId(request.getStreamId());
        if(Greet.meet.equals(request.getMessage())){
            greet.setMessage(Greet.rq0);
        }else if(Greet.rq1.equals(request.getMessage())){
            greet.setMessage(Greet.rs1);
        }else if(Greet.rq2.equals(request.getMessage())){
            greet.setMessage(Greet.rs2);
        }
        return greet;
    }
}

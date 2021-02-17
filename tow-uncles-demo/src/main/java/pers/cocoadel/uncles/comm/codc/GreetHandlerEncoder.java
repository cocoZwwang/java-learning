package pers.cocoadel.uncles.comm.codc;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import pers.cocoadel.uncles.comm.Greet;

public class GreetHandlerEncoder extends MessageToByteEncoder<Greet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Greet msg, ByteBuf out) throws Exception {
        msg.encode(out);
//        System.out.println(msg.getStreamId());
    }
}

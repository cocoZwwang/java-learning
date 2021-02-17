package pers.cocoadel.uncles.comm.codc;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import pers.cocoadel.uncles.comm.Greet;

import java.util.List;

public class GreetHandlerDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Greet greet = new Greet();
        greet.decode(in);
        out.add(greet);
    }
}

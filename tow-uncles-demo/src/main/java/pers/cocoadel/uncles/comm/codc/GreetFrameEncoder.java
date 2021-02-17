package pers.cocoadel.uncles.comm.codc;

import io.netty.handler.codec.LengthFieldPrepender;

import java.nio.ByteOrder;

public class GreetFrameEncoder extends LengthFieldPrepender {
    public GreetFrameEncoder() {
        super(ByteOrder.BIG_ENDIAN, 2, 0, false);
    }
}

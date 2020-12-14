package pers.cocoadel.learning.netty4.server.codec;

import io.netty.handler.codec.LengthFieldPrepender;

import java.nio.ByteOrder;

public class OrderFrameEncoder extends LengthFieldPrepender {
    public OrderFrameEncoder() {
        super(ByteOrder.BIG_ENDIAN, 2, 0, false);
    }
}

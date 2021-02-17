package pers.cocoadel.uncles.comm.codc;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class GreetFrameDecoder extends LengthFieldBasedFrameDecoder {
    public GreetFrameDecoder() {
        super(Integer.MAX_VALUE, 0, 2, 0, 2);
    }
}

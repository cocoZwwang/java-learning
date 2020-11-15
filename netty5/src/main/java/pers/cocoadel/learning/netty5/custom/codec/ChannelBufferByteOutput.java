package pers.cocoadel.learning.netty5.custom.codec;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteOutput;

import java.io.IOException;

public class ChannelBufferByteOutput implements ByteOutput {
    private final ByteBuf byteBuf;

    public ChannelBufferByteOutput(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }


    @Override
    public void write(int b) throws IOException {
        byteBuf.writeByte(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        byteBuf.writeBytes(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        byteBuf.writeBytes(b, off, len);
    }

    @Override
    public void close() throws IOException {
        // nothing to do
    }

    @Override
    public void flush() throws IOException {
        // nothing to do
    }
}

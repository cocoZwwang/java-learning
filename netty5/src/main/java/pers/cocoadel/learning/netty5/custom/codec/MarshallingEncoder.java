package pers.cocoadel.learning.netty5.custom.codec;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteOutput;
import org.jboss.marshalling.Marshaller;

import java.io.IOException;

public class MarshallingEncoder {
    private final static byte[] LENGTH_PAYLOAD = new byte[4];
    private Marshaller marshaller;

    public MarshallingEncoder() {
        try {
            this.marshaller = MarshallingCoderFactory.buildMarshalling();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void encoder(ByteBuf out, Object msg) {
        try {
            int writeIndex = out.writerIndex();
            out.writeBytes(LENGTH_PAYLOAD);
            ByteOutput output = new ChannelBufferByteOutput(out);
            marshaller.start(output);
            marshaller.writeObject(msg);
            marshaller.finish();
            int length = out.writerIndex() - writeIndex - 4;
            out.setInt(writeIndex, length);
        } catch (IOException e) {
           e.printStackTrace();
        }finally {
            try {
                marshaller.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

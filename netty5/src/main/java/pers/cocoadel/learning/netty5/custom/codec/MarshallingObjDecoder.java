package pers.cocoadel.learning.netty5.custom.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

import java.io.IOException;

public class MarshallingObjDecoder {

    Unmarshaller unmarshaller;
    MarshallingObjDecoder(){
        try {
            unmarshaller = MarshallingCoderFactory.buildUnmarshaller();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object decoder(ChannelHandlerContext ctx, ByteBuf in) {
        try {
            int objLen = in.readInt();
            int readIndex = in.readerIndex();
            ByteBuf byteBuf = in.slice(readIndex, objLen);
            ByteInput byteInput = new ChannelBufferByteInput(byteBuf);
            unmarshaller.start(byteInput);
            Object obj = unmarshaller.readObject();
            unmarshaller.finish();
            in.readerIndex(readIndex + objLen);
            return obj;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                unmarshaller.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

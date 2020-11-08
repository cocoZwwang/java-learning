package pers.cocoadel.learning.netty5.coder.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * 序列化
 */
public class MsgPackEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        //使用MessagePack序列化对象
        try {
            MessagePack msgPack = new MessagePack();
            byte[] bytes = msgPack.write(o);
            byteBuf.writeBytes(bytes);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }


}

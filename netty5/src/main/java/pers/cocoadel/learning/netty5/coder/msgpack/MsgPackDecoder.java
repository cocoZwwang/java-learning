package pers.cocoadel.learning.netty5.coder.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * 反序列化
 */
public class MsgPackDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //
        try {
            final byte[] bytes;
            final int length = byteBuf.readableBytes();
            bytes = new byte[length];
            byteBuf.getBytes(byteBuf.readerIndex(), bytes, 0, length);
            MessagePack messagePack = new MessagePack();
            messagePack.register(UserInfo.class);
            list.add(messagePack.read(bytes));
//            list.add(messagePack.read(bytes,UserInfo.class));
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}

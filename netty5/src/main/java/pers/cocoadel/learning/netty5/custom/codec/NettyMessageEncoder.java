package pers.cocoadel.learning.netty5.custom.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;
import pers.cocoadel.learning.netty5.custom.domain.NettyMessage;
import java.util.Map;

public class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage> {
    private final MarshallingEncoder marshallingEncoder;

    public NettyMessageEncoder() {
        this.marshallingEncoder = new MarshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, ByteBuf byteBuf) throws Exception {
        try {
            NettyMessage.Header header = msg.getHeader();
            byteBuf.writeInt(header.getCrcCode());
            byteBuf.writeInt(header.getLength());
            byteBuf.writeLong(header.getSessionID());
            byteBuf.writeByte(header.getType());
            byteBuf.writeByte(header.getPriority());
            Map<String,Object> attachment = header.getAttachment();
            int size = attachment.size();
            byteBuf.writeInt(size);
            if (size > 0) {
                for (Map.Entry<String, Object> entry : attachment.entrySet()) {
                    String key = entry.getKey();
                    Object obj = entry.getValue();
                    byte[] bytes = key.getBytes(CharsetUtil.UTF_8);
                    byteBuf.writeInt(bytes.length);
                    byteBuf.writeBytes(bytes);
                    marshallingEncoder.encoder(byteBuf, obj);
                }
            }
            if(msg.getBody() != null){
                marshallingEncoder.encoder(byteBuf, msg.getBody());
            }else{
                byteBuf.writeInt(0);
            }
            //重新更新消息长度，byteBUf长度 - 4字节crcCode - 4字节Length
            byteBuf.setInt(4, byteBuf.readableBytes() - 8);
        }catch (Throwable throwable){
            throwable.printStackTrace();
            throw throwable;
        }
    }
}

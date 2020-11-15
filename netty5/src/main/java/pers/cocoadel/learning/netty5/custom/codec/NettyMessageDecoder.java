package pers.cocoadel.learning.netty5.custom.codec;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.CharsetUtil;
import pers.cocoadel.learning.netty5.custom.domain.NettyMessage;
import java.util.Map;


public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {
    private final MarshallingObjDecoder marshallingObjDecoder;

    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
        marshallingObjDecoder = new MarshallingObjDecoder();
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        try {
            ByteBuf frame = (ByteBuf) super.decode(ctx, in);
            if (frame == null) {
                return null;
            }

            NettyMessage nettyMessage = NettyMessage.createNettyMessage();
            NettyMessage.Header header = nettyMessage.getHeader();
            header.setCrcCode(frame.readInt());
            header.setLength(frame.readInt());
            header.setSessionID(frame.readLong());
            header.setType(frame.readByte());
            header.setPriority(frame.readByte());
            int size = frame.readInt();
            Map<String, Object> map = header.getAttachment();
            while (size-- > 0) {
                int keySize = frame.readInt();
                byte[] keyBytes = new byte[keySize];
                frame.readBytes(keyBytes, frame.readerIndex(), keySize);
                String key = new String(keyBytes, CharsetUtil.UTF_8);
                Object obj = marshallingObjDecoder.decoder(ctx, frame);
                map.put(key, obj);
            }
            if(frame.readableBytes() > 4){
                nettyMessage.setBody(marshallingObjDecoder.decoder(ctx, frame));
            }
            return nettyMessage;
        }catch (Throwable throwable){
            throwable.printStackTrace();
            throw throwable;
        }
    }
}

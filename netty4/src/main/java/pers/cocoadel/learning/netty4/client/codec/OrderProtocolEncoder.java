package pers.cocoadel.learning.netty4.client.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import pers.cocoadel.learning.netty4.common.RequestMessage;
import pers.cocoadel.learning.netty4.common.ResponseMessage;

public class OrderProtocolEncoder extends MessageToByteEncoder<RequestMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RequestMessage requestMessage, ByteBuf byteBuf) throws Exception {
        requestMessage.encoder(byteBuf);
    }
}

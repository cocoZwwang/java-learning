package pers.cocoadel.learning.netty4.client.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import pers.cocoadel.learning.netty4.client.dispatcher.RequestPendingCenter;
import pers.cocoadel.learning.netty4.common.ResponseMessage;

import java.util.List;

public class OrderProtocolDecoder extends ByteToMessageDecoder {
    private final RequestPendingCenter requestPendingCenter;

    public OrderProtocolDecoder(RequestPendingCenter requestPendingCenter) {
        this.requestPendingCenter = requestPendingCenter;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext,
                          ByteBuf byteBuf, List<Object> list) throws Exception {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.decode(byteBuf);
        list.add(responseMessage);
        requestPendingCenter.set(responseMessage.getMessageHeader().getStreamId(), responseMessage);
//        System.out.println(JsonUtil.toJson(responseMessage.getMessageBody()));
    }
}

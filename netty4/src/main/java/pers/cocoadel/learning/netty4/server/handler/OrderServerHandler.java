package pers.cocoadel.learning.netty4.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import pers.cocoadel.learning.netty4.common.*;

@Slf4j
public class OrderServerHandler extends SimpleChannelInboundHandler<RequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage requestMessage) throws Exception {
        OperationResult result = requestMessage.getMessageBody().executor();
//        ByteBuf byteBuf = ctx.alloc().buffer();
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessageBody(result);
        MessageHeader messageHeader = requestMessage.getMessageHeader();
        responseMessage.setMessageHeader(messageHeader);
        //如果当前通道是可写，就写入
        if(ctx.channel().isActive() && ctx.channel().isWritable()){
            ctx.write(responseMessage);
        }else{
            //如果当前通过不可写，则丢弃然后打印日志
            //或者也可以选择存储起来，之后再处理
            log.error("dropped message: " + responseMessage);
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}

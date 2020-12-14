package pers.cocoadel.learning.netty4.client.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import pers.cocoadel.learning.netty4.common.RequestMessage;
import pers.cocoadel.learning.netty4.common.keeplive.KeepLiveOperation;
import pers.cocoadel.learning.netty4.util.IdUtil;

@ChannelHandler.Sharable
@Slf4j
public class KeepaliveHandler extends ChannelDuplexHandler {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt == IdleStateEvent.FIRST_WRITER_IDLE_STATE_EVENT){
            RequestMessage requestMessage = RequestMessage.createRequestMessage(IdUtil.next(),new KeepLiveOperation());
            ctx.writeAndFlush(requestMessage);
            log.info("write idle happen,so need to send the keepalive message to keep  connection not closed by server!");
        }
        super.userEventTriggered(ctx, evt);
    }
}

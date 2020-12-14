package pers.cocoadel.learning.netty4.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class ServerIdleCheckHandler extends IdleStateHandler {
    //如10秒没有接受到数据，则断开连接
    public ServerIdleCheckHandler() {
        super(10, 0,0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        //如果接收到10秒没有读数据的idle事件，关闭通道。
        if(evt == IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT){
            ctx.close();
            return;
        }
        super.channelIdle(ctx, evt);
    }
}

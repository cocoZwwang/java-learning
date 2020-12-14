package pers.cocoadel.learning.netty4.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import pers.cocoadel.learning.netty4.common.Operation;
import pers.cocoadel.learning.netty4.common.RequestMessage;
import pers.cocoadel.learning.netty4.common.auth.AuthOperation;
import pers.cocoadel.learning.netty4.common.auth.AuthOperationResult;

@Slf4j
@ChannelHandler.Sharable
public class AuthHandler extends SimpleChannelInboundHandler<RequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage msg) throws Exception {
        try {
            doAuth(ctx, msg);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ctx.pipeline().remove(this);
        }
    }

    private void doAuth(ChannelHandlerContext ctx, RequestMessage msg) {
        Operation operation = msg.getMessageBody();
        if (operation instanceof AuthOperation) {
            AuthOperation authOperation = (AuthOperation) operation;
            AuthOperationResult result = authOperation.executor();
            if (result.isPass()) {
                log.info("auth success!");
            } else {
                log.info("auth fail");
                ctx.close();
            }
        } else {
            log.error("except the first request is auth request");
            ctx.close();
        }
    }
}

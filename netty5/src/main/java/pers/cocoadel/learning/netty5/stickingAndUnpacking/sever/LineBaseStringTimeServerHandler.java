package pers.cocoadel.learning.netty5.stickingAndUnpacking.sever;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import sun.nio.cs.ext.MS874;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * 使用了{@link io.netty.handler.codec.LineBasedFrameDecoder}和
 * {@link io.netty.handler.codec.string.StringDecoder}的消息处理
 */
public class LineBaseStringTimeServerHandler extends ChannelHandlerAdapter {
    private int counts = 0;
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String req = (String) msg;
        System.out.printf("[count:%s]req: %s\n",++counts,req);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(req) ?
                new Date(System.currentTimeMillis()).toString() :
                "BAD ORDER";
        //记得要加分隔符，否则LineBasedFrameDecoder无法根据换行符进行分割
        currentTime = currentTime + System.lineSeparator();
        ByteBuf bytebuf = Unpooled.copiedBuffer(currentTime.getBytes(CharsetUtil.UTF_8));
        ctx.writeAndFlush(bytebuf);
    }
}

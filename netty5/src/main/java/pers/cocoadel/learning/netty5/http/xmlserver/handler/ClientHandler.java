package pers.cocoadel.learning.netty5.http.xmlserver.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import pers.cocoadel.learning.netty5.http.xmlserver.domain.Order;
import pers.cocoadel.learning.netty5.http.xmlserver.domain.OrderFactory;
import pers.cocoadel.learning.netty5.http.xmlserver.http.FullHttpXmlRequest;
import pers.cocoadel.learning.netty5.http.xmlserver.http.FullHttpXmlResponse;

public class ClientHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        FullHttpXmlRequest xmlRequest = new FullHttpXmlRequest();
        Order order = OrderFactory.createOrder(11);
        xmlRequest.setBody(order);
        ctx.writeAndFlush(xmlRequest);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpXmlResponse response = (FullHttpXmlResponse) msg;
        Object object = response.getBody();
        System.out.println("the message is received from server**********");
        System.out.println(object);
        System.out.println("**********************************************");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }
}

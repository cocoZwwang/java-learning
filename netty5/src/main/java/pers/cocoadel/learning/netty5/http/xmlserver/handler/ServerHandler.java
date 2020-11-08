package pers.cocoadel.learning.netty5.http.xmlserver.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.omg.CORBA.ORB;
import pers.cocoadel.learning.netty5.http.xmlserver.domain.Address;
import pers.cocoadel.learning.netty5.http.xmlserver.domain.Customer;
import pers.cocoadel.learning.netty5.http.xmlserver.domain.Order;
import pers.cocoadel.learning.netty5.http.xmlserver.http.FullHttpXmlRequest;
import pers.cocoadel.learning.netty5.http.xmlserver.http.FullHttpXmlResponse;
import java.util.Arrays;
import java.util.List;

public class ServerHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (ctx.channel().isActive()) {
            sendError(ctx,HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpXmlRequest request = (FullHttpXmlRequest) msg;
        Order order = (Order) request.getBody();
        System.out.println("the message is received from the client************");
        System.out.println(order);
        System.out.println("***************************************************");
        doBusiness(order);
        FullHttpXmlResponse xmlResponse = new FullHttpXmlResponse();
        xmlResponse.setBody(order);
        ChannelFuture future = ctx.writeAndFlush(xmlResponse);
        if (!HttpHeaders.isKeepAlive(request.getRequest())) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status,
                Unpooled.copiedBuffer("失败：" + status.toString() + "\r\n", CharsetUtil.UTF_8));
        response.headers().add(HttpHeaders.Names.CONTENT_TYPE, "text/plain;charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private void doBusiness(Order order) {
        Customer customer = order.getCustomer();
        customer.setFirstName("Schnee");
        customer.setLastName("Weiss");
        List<String> middleNames = Arrays.asList("YangXiaoLong","Black Belladonna");
        customer.setMiddleNames(middleNames);
        Address address = order.getShipTo();
        address.setStreet1("信标学院D110");
        address.setStreet1("信标学院B110");
    }
}

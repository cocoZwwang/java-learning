package pers.cocoadel.learning.netty5.websockt;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.apache.log4j.Logger;

import java.util.Date;

public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
    private WebSocketServerHandshaker handshaker;
    private Logger logger = Logger.getLogger(WebSocketServerHandler.class);

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            handlerHttpRequest(ctx, (FullHttpRequest) msg);
        } else {
            handlerWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private void handlerHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        //如果Http解码失败，则返回Http异常
        if (!request.getDecoderResult().isSuccess() || !"websocket".equals(request.headers().get("Upgrade"))) {
            sendHttpResponse(ctx, request,
                    new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
        }

        //构造握手响应返回，本机测试，直接使用localhost
        String url = "ws://localhost:8807/websocket";
        WebSocketServerHandshakerFactory factory =
                new WebSocketServerHandshakerFactory(url, null, false);
        handshaker = factory.newHandshaker(request);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), request);
        }
    }

    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        //判断是否为关闭链路指令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), ((CloseWebSocketFrame) frame).retain());
            return;
        }
        //判断是否是Ping消息
        if (frame instanceof PongWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        //本例子仅支持文本传输，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(String.format("%s frame types not supported",
                    frame.getClass().getName()));
        }
        //返回应答消息
        String request = ((TextWebSocketFrame) frame).text();
        System.out.println(String.format("%s  receive %s", ctx.channel(), request));
        ctx.channel().write(new TextWebSocketFrame(request +
                ", 欢迎使用Netty WebSocket服务，现在时刻是：" + new Date(System.currentTimeMillis())));

    }

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request, FullHttpResponse response) {
        if (response.getStatus().code() != 200) {
            byte[] bytes = response.getStatus().toString().getBytes(CharsetUtil.UTF_8);
            response.content().writeBytes(bytes);
            response.headers().add(HttpHeaders.Names.CONTENT_LENGTH, bytes.length);
        }

        ChannelFuture cf = ctx.writeAndFlush(response);
        //如果不是200或者不是keeplive长连接，服务端发送响应完后，直接关闭。
        if (!HttpHeaders.isKeepAlive(request) || response.getStatus().code() != 200) {
            cf.addListener(ChannelFutureListener.CLOSE);
        }
    }


}

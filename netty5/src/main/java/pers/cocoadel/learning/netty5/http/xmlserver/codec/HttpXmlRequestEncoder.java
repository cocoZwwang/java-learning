package pers.cocoadel.learning.netty5.http.xmlserver.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import pers.cocoadel.learning.netty5.http.xmlserver.http.FullHttpXmlRequest;
import java.net.InetAddress;
import java.util.List;

public class HttpXmlRequestEncoder extends AbstractHttpXmlEncoder<FullHttpXmlRequest> {


    @Override
    protected void encode(ChannelHandlerContext ctx, FullHttpXmlRequest msg, List<Object> out) throws Exception {
        Object body = msg.getBody();
        //如果上层应用层没有传递请求头，则自己构建
        ByteBuf byteBuf = encode0(ctx, body);
        byteBuf = byteBuf == null ? Unpooled.buffer(0) : byteBuf;
        FullHttpRequest request =
                new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/do", byteBuf);
        request.headers().add(HttpHeaders.Names.HOST, InetAddress.getLocalHost());
        request.headers().add(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
        request.headers().add(HttpHeaders.Names.ACCEPT_ENCODING, HttpHeaders.Values.GZIP.toString() + "," +
                HttpHeaders.Values.DEFLATE.toString());
        request.headers().add(HttpHeaders.Names.ACCEPT_CHARSET, "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
        request.headers().add(HttpHeaders.Names.ACCEPT_LANGUAGE, "zh");
        request.headers().add(HttpHeaders.Names.USER_AGENT, "Netty xml Http Client size");
        request.headers().add(HttpHeaders.Names.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        if (msg.getRequest() != null) {
            HttpHeaders headers = msg.getRequest().headers();
            request.headers().add(headers);
        }
        request.headers().add(HttpHeaders.Names.CONTENT_LENGTH, byteBuf.readableBytes());
        out.add(request);
    }
}

package pers.cocoadel.learning.netty5.http.xmlserver.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import pers.cocoadel.learning.netty5.http.xmlserver.http.FullHttpXmlResponse;

import java.util.List;

public class HttpXmlResponseEncoder extends AbstractHttpXmlEncoder<FullHttpXmlResponse> {

    @Override
    protected void encode(ChannelHandlerContext ctx, FullHttpXmlResponse msg, List<Object> out) throws Exception {
        ByteBuf byteBuf = encode0(ctx, msg.getBody());
        HttpVersion httpVersion = HttpVersion.HTTP_1_1;
        HttpResponseStatus status = HttpResponseStatus.OK;
        if (msg.getResponse() != null) {
            httpVersion = msg.getResponse().getProtocolVersion();
            status = msg.getResponse().getStatus();
        }
        FullHttpResponse response = new DefaultFullHttpResponse(httpVersion, status, byteBuf);
        response.headers().add(HttpHeaders.Names.CONTENT_LENGTH, byteBuf.readableBytes());
        response.headers().add(HttpHeaders.Names.CONTENT_TYPE, "text/xml");
        out.add(response);
    }
}

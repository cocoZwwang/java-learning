package pers.cocoadel.learning.netty5.http.xmlserver.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;
import pers.cocoadel.learning.netty5.http.xmlserver.http.FullHttpXmlResponse;

import java.util.List;

/**
 * {@link FullHttpResponse} 转换成{@link FullHttpXmlResponse}
 */
public class HttpXmlResponseDecoder extends AbstractHttpXmlDecoder<FullHttpResponse> {

    public HttpXmlResponseDecoder(Class<?> clazz) {
        super(clazz);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, FullHttpResponse msg, List<Object> out) throws Exception {
        FullHttpXmlResponse xmlResponse = new FullHttpXmlResponse();
        xmlResponse.setResponse(msg);
        Object obj = decode0(ctx, msg.content());
        xmlResponse.setBody(obj);
        out.add(xmlResponse);
    }
}

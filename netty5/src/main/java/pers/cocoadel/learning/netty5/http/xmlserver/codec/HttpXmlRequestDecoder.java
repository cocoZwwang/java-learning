package pers.cocoadel.learning.netty5.http.xmlserver.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.CharsetUtil;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.JiBXException;
import pers.cocoadel.learning.netty5.http.xmlserver.http.FullHttpXmlRequest;

import java.io.StringReader;
import java.util.List;

/**
 * 把{@link FullHttpRequest}转换成{@link FullHttpXmlRequest}
 */
public class HttpXmlRequestDecoder extends AbstractHttpXmlDecoder<FullHttpRequest> {

    public HttpXmlRequestDecoder(Class<?> clazz) {
        super(clazz);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, FullHttpRequest msg, List<Object> out) throws Exception {
        if (msg.getDecoderResult().isSuccess()) {
            ByteBuf byteBuf = msg.content();
            Object object = decode0(ctx, byteBuf);
            FullHttpXmlRequest xmlRequest = new FullHttpXmlRequest();
            xmlRequest.setRequest(msg);
            xmlRequest.setBody(object);
            out.add(xmlRequest);
        }
    }
}

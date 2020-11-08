package pers.cocoadel.learning.netty5.http.xmlserver.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;
import java.io.StringReader;

public abstract class AbstractHttpXmlDecoder<T> extends MessageToMessageDecoder<T> {

    private Class<?> clazz;

    AbstractHttpXmlDecoder(Class<?> clazz) {
        this.clazz = clazz;
    }


    protected Object decode0(ChannelHandlerContext ctx,Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String xml = new String(bytes, CharsetUtil.UTF_8);
        try (StringReader reader = new StringReader(xml)) {
            IBindingFactory factory = BindingDirectory.getFactory(clazz);
            IUnmarshallingContext imctx = factory.createUnmarshallingContext();
            return imctx.unmarshalDocument(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

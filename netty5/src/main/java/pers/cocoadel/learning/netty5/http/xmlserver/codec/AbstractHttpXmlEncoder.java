package pers.cocoadel.learning.netty5.http.xmlserver.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;

import java.io.IOException;
import java.io.StringWriter;

public abstract class AbstractHttpXmlEncoder<T> extends MessageToMessageEncoder<T> {


    protected ByteBuf encode0(ChannelHandlerContext ctx, Object msg) {
        StringWriter writer = null;
        try {
            Class<?> clazz = msg.getClass();
            IBindingFactory iBindingFactory = BindingDirectory.getFactory(clazz);
            IMarshallingContext mctx =  iBindingFactory.createMarshallingContext();
            mctx.setIndent(2);
            writer = new StringWriter();
            mctx.marshalDocument(msg, CharsetUtil.UTF_8.displayName(), null, writer);
            String xml = writer.toString();
//            System.out.printf("encode [class: %s] msg to xml: %s\n", clazz.getName(), xml);
            return Unpooled.wrappedBuffer(xml.getBytes(CharsetUtil.UTF_8));
        } catch (Throwable e) {
            e.printStackTrace();
        }finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Unpooled.buffer(0);
    }
}

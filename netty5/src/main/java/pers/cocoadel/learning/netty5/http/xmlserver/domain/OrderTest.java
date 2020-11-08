package pers.cocoadel.learning.netty5.http.xmlserver.domain;

import io.netty.util.CharsetUtil;
import org.jibx.runtime.*;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class OrderTest {

    private static String encode2Xml(Order order) {
        StringWriter writer = null;
        try {
            IBindingFactory factory = BindingDirectory.getFactory(Order.class);
            writer = new StringWriter();
            IMarshallingContext mctx = factory.createMarshallingContext();
            mctx.setIndent(2);
            mctx.marshalDocument(order, CharsetUtil.UTF_8.displayName(), null, writer);
            return writer.toString();
        } catch (Exception e) {
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
        return null;
    }

    private static Order decode2Object(String xmlStr) {
        StringReader reader = null;
        try {
            IBindingFactory factory = BindingDirectory.getFactory(Order.class);
            reader = new StringReader(xmlStr);
            IUnmarshallingContext imctx = factory.createUnmarshallingContext();
            return (Order) imctx.unmarshalDocument(reader);
        } catch (JiBXException e) {
            e.printStackTrace();
        }finally {
            if (reader != null) {
                reader.close();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Order order = OrderFactory.createOrder(1);
        System.out.println("初始order：" + order);
        String xmlStr = encode2Xml(order);
        System.out.println("order 对象编码后XMl字符串：" + xmlStr);
        order = decode2Object(xmlStr);
        System.out.println("解码后order：" + order);
    }
}

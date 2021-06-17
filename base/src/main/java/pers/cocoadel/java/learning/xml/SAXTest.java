package pers.cocoadel.java.learning.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class SAXTest {


    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("请输入 xml 资源路径：");
            //https://www.w3.org
            String uri = scanner.nextLine();

            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            SAXParser saxParser = factory.newSAXParser();
            InputStream in = new URL(uri).openStream();
            saxParser.parse(in, new MyHandler());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class MyHandler extends DefaultHandler{
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException {
            //打印所有 a 标签的 href 属性的值
            if(localName.equals("a") && attributes != null){
                for (int i = 0; i < attributes.getLength(); i++) {
                    String name = attributes.getLocalName(i);
                    if (name.equals("href")) {
                        System.out.println(attributes.getValue(i));
                    }
                }
            }
        }
    }
}

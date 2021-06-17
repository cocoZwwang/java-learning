package pers.cocoadel.java.learning.xml;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class StAxTest {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("请输入 xml 资源路径：");
            String urlStr = scanner.nextLine();

            URL url = new URL(urlStr);
            InputStream inputStream = url.openStream();
            XMLInputFactory inputFactory = XMLInputFactory.newFactory();
            XMLStreamReader xmlStreamReader = inputFactory.createXMLStreamReader(inputStream);
            while(xmlStreamReader.hasNext()){
                int event = xmlStreamReader.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    if (xmlStreamReader.getLocalName().equals("a")) {
                        String href = xmlStreamReader.getAttributeValue(null, "href");
                        if (href != null) {
                            System.out.println(href);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

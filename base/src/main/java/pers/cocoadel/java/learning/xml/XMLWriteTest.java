package pers.cocoadel.java.learning.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;

public class XMLWriteTest {

    private static final Random RANDOM = new Random();
    private static final String fileDir = "E:\\Projects\\java\\java-learning\\base\\src\\main\\resources\\pers\\cocoadel\\java\\learning\\xml";
    public static void main(String[] args) throws Exception {
        //通过先构建 dom 树，然后输入文件
        Document document = newDrawingDocument(600, 400);
        writeDocument(document,fileDir + "/drawing1.svg");

        //通过 stax 输出流 API 进行编写
        writeNewDrawing(600, 400, fileDir + "/drawing2.svg");
    }

    private static void writeDocument(Document document,String fileName) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        //设置 dtd 外部文档
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,
                "http://www.w3.org/TR/2000/CR-SVG-20000802/DTD/svg-20000802.dtd");

        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "-//W3C//DTD SVG 20000802//EN");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(new DOMSource(document),
                new StreamResult(Files.newOutputStream(Paths.get(fileName))));
    }

    private static Document newDrawingDocument(int drawWidth, int drawHeight) throws Exception {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        //设置命名空间感知
        builderFactory.setNamespaceAware(true);

        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        //创建一个 document 对象
        Document document = builder.newDocument();
        String nameSpaces = "http://www.w3.org/2000/svg";
        // 创建根节点
        Element rootElement = document.createElementNS(nameSpaces, "svg");
        document.appendChild(rootElement);
        rootElement.setAttribute("width", "" + drawWidth);
        rootElement.setAttribute("height", "" + drawHeight);

        int n = 10 + RANDOM.nextInt(20);
        for(int i = 0; i < n; i++){
            int x = RANDOM.nextInt(drawWidth);
            int y = RANDOM.nextInt(drawHeight);

            int width = drawWidth - x;
            int height = drawHeight - y;
            int r = RANDOM.nextInt(256);
            int g = RANDOM.nextInt(256);
            int b = RANDOM.nextInt(256);

            Element rect = document.createElementNS(nameSpaces, "rect");
            rootElement.appendChild(rect);
            rect.setAttribute("x", x + "");
            rect.setAttribute("y", y +"");
            rect.setAttribute("width", width + "");
            rect.setAttribute("height", height + "");
            rect.setAttribute("fill", String.format("#%02x%02x%02x", r, g, b));
        }
        return document;
    }

    private static void writeNewDrawing(int drawWidth, int drawHeight, String fileName) throws Exception {
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
        XMLStreamWriter writer = xmlOutputFactory.createXMLStreamWriter(Files.newOutputStream(Paths.get(fileName)));

        writer.writeStartDocument();

        //写入 DTD
        String dtd = "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 20000802//EN\" " +
                "\"http://www.w3.org/TR/2000/CR-SVG-20000802/DTD/svg-20000802.dtd\">";
        writer.writeDTD(dtd);
        writer.writeStartElement("svg");
        writer.writeDefaultNamespace("http://www.w3.org/2000/svg");
        writer.writeAttribute("width", "" + drawWidth);
        writer.writeAttribute("height", "" + drawHeight);
        int n = 10 + RANDOM.nextInt(20);
        for(int i = 0; i < n; i++){
            int x = RANDOM.nextInt(drawWidth);
            int y = RANDOM.nextInt(drawHeight);

            int width = drawWidth - x;
            int height = drawHeight - y;
            int r = RANDOM.nextInt(256);
            int g = RANDOM.nextInt(256);
            int b = RANDOM.nextInt(256);

            writer.writeEmptyElement("rect");
            writer.writeAttribute("x", x + "");
            writer.writeAttribute("y", y + "");
            writer.writeAttribute("width", width + "");
            writer.writeAttribute("height", height + "");
            writer.writeAttribute("fill", String.format("#%02x%02x%02x", r, g, b));
        }
        writer.writeEndDocument();
    }
}

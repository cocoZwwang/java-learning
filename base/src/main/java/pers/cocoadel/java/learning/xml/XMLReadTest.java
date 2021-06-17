package pers.cocoadel.java.learning.xml;

import org.w3c.dom.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class XMLReadTest {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            //输入文件名
            System.out.println("请输入 xml 的文件路径：");
            String fileName = scanner.nextLine();

            //创建 document 解析器
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setIgnoringElementContentWhitespace(true);
            builderFactory.setValidating(true);

            //设置 schema 的配置
            if (fileName.contains("-schema")) {
                builderFactory.setNamespaceAware(true);
                final String jaxp_schema_language = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
                final String w3c_xml_schema = "http://www.w3.org/2001/XMLSchema";
                builderFactory.setAttribute(jaxp_schema_language, w3c_xml_schema);
            }

            // 创建 documentBuilder
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            documentBuilder.setErrorHandler(new MyErrorHandler());

            // 创建 document
            Document document = documentBuilder.parse(fileName);
            Map<String, Object> configs = parseConfig(document.getDocumentElement());
            System.out.println("configs = " + configs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String,Object> parseConfig(Element element) throws Exception {
        Map<String, Object> result = new HashMap<>();
        NodeList childNodes = element.getChildNodes();
        for(int i = 0; i < childNodes.getLength(); i++){
            Element item = (Element) childNodes.item(i);
            String id = item.getAttribute("id");
            Object value = parseObject((Element) item.getFirstChild());
            result.put(id,value);
        }
        return result;
    }

    private static Object parseObject(Element element) throws Exception {
        String tag = element.getTagName();
        if ("factory".equals(tag)) {
            return parseFactory(element);
        } else if ("construct".equals(tag)) {
            return parseConstruct(element);
        } else {
            CharacterData characterData = (CharacterData) element.getFirstChild();
            String data = characterData.getData();
            if ("int".equals(tag)) {
                return Integer.valueOf(data);
            } else if ("boolean".equals(tag)) {
                return Boolean.valueOf(data);
            }
            return data;
        }
    }

    private static Object parseConstruct(Element element) throws Exception {
        String className = element.getAttribute("class");
        Class<?> clazz = Class.forName(className);
        Object[] arguments = parseArguments(element);
        Class<?>[] argumentTypes = getArgumentTypes(arguments);
        Constructor<?> constructor = clazz.getConstructor(argumentTypes);
        return constructor.newInstance(arguments);
    }

    private static Object parseFactory(Element element) throws Exception{
        String className = element.getAttribute("class");
        String methodName = element.getAttribute("method");
        Class<?> clazz = Class.forName(className);
        Object[] arguments = parseArguments(element);
        Class<?>[] argumentTypes = getArgumentTypes(arguments);
        Method method = clazz.getDeclaredMethod(methodName, argumentTypes);
        return method.invoke(null, arguments);
    }

    private static final Map<Class<?>, Class<?>> toPrimitive = new HashMap<>();
    static {
        toPrimitive.put(Integer.class, int.class);
        toPrimitive.put(Boolean.class, Boolean.class);
    }

    private static Object[] parseArguments(Element element) throws Exception {
        NodeList childNodes = element.getChildNodes();
        Object[] arguments = new Object[childNodes.getLength()];
        for(int i = 0; i < childNodes.getLength(); i++){
            Element item = (Element) childNodes.item(i);
            arguments[i] = parseObject(item);
        }
        return arguments;
    }

    private static Class<?>[] getArgumentTypes(Object[] arguments) {
        Class<?>[] result = new Class<?>[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            Class<?> clazz = arguments[i].getClass();
            Class<?> primitive = toPrimitive.get(clazz);
            result[i] = primitive == null ? clazz : primitive;
        }
        return result;
    }

    private static class MyErrorHandler implements ErrorHandler {

        @Override
        public void warning(SAXParseException exception) throws SAXException {
            System.out.println("exception = " + exception);
        }

        @Override
        public void error(SAXParseException exception) throws SAXException {
            System.out.println("exception = " + exception);
            System.exit(0);
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            System.out.println("exception = " + exception);
            System.exit(0);
        }
    }
}

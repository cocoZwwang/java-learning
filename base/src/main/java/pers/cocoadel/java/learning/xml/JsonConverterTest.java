package pers.cocoadel.java.learning.xml;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class JsonConverterTest {

    private static final Map<Character, String> replacements = new HashMap<>();

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            //读入文件名称
            System.out.println("请输入 xml 文件地址：");
            String pathStr = scanner.nextLine();

            //创建 DOM 对象，并解析输入路径的 xml 文件
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(pathStr);

            //获取根节点
            Element root = doc.getDocumentElement();

            //初始化转义的映射
            initReplacements();

            //从根节点进行遍历转换：递归入口
            String json = convert(root, 0).toString();
            System.out.println("json = " + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initReplacements() {
        replacements.put('\b',"\\b");
        replacements.put('\f',"\\f");
        replacements.put('\n',"\\n");
        replacements.put('\r',"\\r");
        replacements.put('\t',"\\t");
        replacements.put('"',"\\\"");
        replacements.put('\\',"\\\\");
    }

    /**
     * 递归转换节点
     * @param node xml dom 节点
     * @param level 格式化输出的深度
     */
    private static StringBuilder convert(Node node, int level) {
        if (node instanceof Element) {
            return elementObject((Element) node, level);
        }

        if (node instanceof CharacterData) {
            return characterString((CharacterData) node, level);
        }
        System.out.println("第三种：" + node.getClass().getName());
        return pad(new StringBuilder(), level)
                .append(jsonEscape(node.getClass().getName()));
    }

    /**
     * 对字符串进行转义
     */
    private static StringBuilder jsonEscape(String str){
        StringBuilder result = new StringBuilder("\"");
        for (char ch : str.toCharArray()) {
            String replacement = replacements.get(ch);
            if (replacement == null) {
                result.append(ch);
            }else {
                result.append(replacement);
            }
        }
        result.append("\"");
        return result;
    }

    /**
     * 文本内容处理
     */
    private static StringBuilder characterString(CharacterData node, int level){
        StringBuilder result = new StringBuilder();
        StringBuilder data = jsonEscape(node.getData());
        if(node instanceof Comment){
            data.insert(1,"Comment:");
        }
        pad(result, level).append(data);
        return result;
    }

    /**
     * 元素节点处理
     */
    private static StringBuilder elementObject(Element element, int level) {
        StringBuilder result = new StringBuilder();
        pad(result,level).append("{\n");
        pad(result, level + 1)
                .append("\"name\": ")
                .append(jsonEscape(element.getTagName()));
        NamedNodeMap attributes = element.getAttributes();
        if (attributes.getLength() > 0) {
            pad(result.append(",\n"), level + 1)
                    .append("\"attributes\": ")
                    .append(attributeObject(attributes));
        }
        NodeList childNodes = element.getChildNodes();
        if (childNodes.getLength() > 0) {
            pad(result.append(",\n"),level + 1).append("\"children\": [\n");
            for (int i = 0; i < childNodes.getLength(); i++) {
                if (i > 0) {
                    result.append(",\n");
                }
                Node item = childNodes.item(i);
//                System.out.println("item = " + item.getClass().getName());
                result.append(convert(item, level + 2));
            }
            pad(result.append("\n"),level + 1).append("]");
        }
        result.append("\n");
        pad(result,level).append("}");
        return result;
    }

    /**
     * 格式化退格
     */
    private static StringBuilder pad(StringBuilder sb, int level){
        for(int i = 0; i < level; i++){
            sb.append("  ");
        }
        return sb;
    }

    /**
     * 处理属性
     */
    private static StringBuilder attributeObject(NamedNodeMap attr){
        StringBuilder result = new StringBuilder();
        result.append("{");
        for (int i = 0; i < attr.getLength(); i++) {
            if (i > 0) {
                result.append(", ");
            }
            Node item = attr.item(i);
            result.append(jsonEscape(item.getNodeName()));
            result.append(": ");
            result.append(jsonEscape(item.getNodeValue()));
        }
        result.append("}");
        return result;
    }
}

package per.cocoadel.learing.jvm.memory.error;

import java.util.ArrayList;
import java.util.List;

/**
 * 运行时常量池内存溢出
 * VM args: -XX:PermSize=10m -XX:MaxPermSize=10m
 * 需要JDK1.6以下，因为jdk1.7后{@link String#intern()}方法做了修改
 * JDK1.6：String对象如果不在常量池，intern（）方法会在常量池复制一份对象，如果存在，直接返回常量池的对象引用
 * JDK1.7后：String#intern（）方法不在复制对象，而是复制引用，对象都存储在堆中。
 */
public class RuntimeConstantPoolOOM {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        int i = 0;
        while(true){
            list.add(String.valueOf(i++).intern());
        }
    }
}

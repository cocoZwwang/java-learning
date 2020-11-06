package per.cocoadel.learing.jvm.memory.error;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 直接内存溢出测试
 * 直接内存溢出，一个明显的特征是在Heap Dump文件中不会看见明显的异常，如果发现OOM之后Dump文件很小，而且程序中又使用了NIO，可以考虑检查一下这方面的原因
 * VM args： -Xmx20M -XX:MaxDirectMemorySize=10M
 */
public class DirectMemoryOOM {
    private static final int _1MB = 1024 * 1024;
    public static void main(String[] args) throws IllegalAccessException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true){
            unsafe.allocateMemory(_1MB);
        }

    }
}

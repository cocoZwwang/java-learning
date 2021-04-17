package pers.cocoadel.java.learning.GenerateType;

import pers.cocoadel.java.learning.GenerateType.bean.CommConverter;
import pers.cocoadel.java.learning.GenerateType.bean.IntegerConvert;
import pers.cocoadel.java.learning.GenerateType.bean.UserConverter;
import java.lang.reflect.Method;


public class ReflectGenerateTypeTest {

    public static void main(String[] args) {
//        ReflectGenerateTypeUtils.printClass(MyList.class);
        ReflectGenerateTypeUtils.printClass(CommConverter.class);
        printAllMethods(CommConverter.class);

        System.out.println("**************************************");

        ReflectGenerateTypeUtils.printClass(IntegerConvert.class);
        printAllMethods(IntegerConvert.class);

        System.out.println("**************************************");

        ReflectGenerateTypeUtils.printClass(UserConverter.class);
        printAllMethods(UserConverter.class);
    }


    private static void printAllMethods(Class clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            ReflectGenerateTypeUtils.printMethod(method);
            System.out.println();
        }
    }
}

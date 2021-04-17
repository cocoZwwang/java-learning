package pers.cocoadel.java.learning.GenerateType;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ReflectGenerateTypeUtils {

    public static void printClass(Class clazz) {
        print(clazz.getName());
        printTypes(clazz.getTypeParameters(), "<", ",", ">", true);
        print(" extends ");
        printType(clazz.getGenericSuperclass(), false);
        printTypes(clazz.getGenericInterfaces(), " implements ", ",", "", false);
        print("\n");
    }

    public static void printMethod(Method method) {
        print(Modifier.toString(method.getModifiers()));
        printTypes(method.getTypeParameters(), " <", ",", ">", true);
        print(" ");
        printType(method.getGenericReturnType(), false);
        print(" ");
        print(method.getName());
        print(" (");
        printTypes(method.getGenericParameterTypes(), "", ",", "", false);
        print(")");
    }

    public static void printTypes(Type[] types, String pre, String sep, String suf, boolean isDefinition) {
        if ("extends".equals(pre) && Arrays.equals(types, new Type[]{Object.class})) {
            return;
        }

        if (types.length > 0) {
            print(pre);
        }

        for (int i = 0; i < types.length; i++) {
            if (i > 0) {
                print(sep);
            }
            printType(types[i], isDefinition);
        }

        if (types.length > 0) {
            print(suf);
        }

    }

    public static void printType(Type type, boolean isDefinition) {
        if (type instanceof Class) {
            Class clazz = (Class) type;
            print(clazz.getName());
            return;
        }

        if (type instanceof TypeVariable) {
            TypeVariable typeVariable = (TypeVariable) type;
            print(typeVariable.getName());
            if (isDefinition) {
                printTypes(typeVariable.getBounds(), "extends", "&", "", false);
            }
            return;
        }

        if (type instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type;
            print("?");
            printTypes(wildcardType.getUpperBounds(), "extends", "&", "", false);
            printTypes(wildcardType.getLowerBounds(),"super","&","",false);
            return;
        }

        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
//            Type ownerType = parameterizedType.getOwnerType();
//            if (ownerType != null) {
//                printType(ownerType, false);
//                print(".");
//            }

            Type rawType = parameterizedType.getRawType();
            printType(rawType, false);
            printTypes(parameterizedType.getActualTypeArguments(), "<", ",", ">", false);
            return;
        }

        if (type instanceof GenericArrayType) {
            GenericArrayType arrayType = (GenericArrayType) type;
            print("");
            printType(arrayType.getGenericComponentType(), isDefinition);
            print("[]");
        }
    }

    private static void print(String text) {
        System.out.print(text);
    }
}

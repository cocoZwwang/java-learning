package pers.cocoadel.java.learning.GenerateType;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;

public class ReflectGenerateTypeDemo {
    public static void main(String[] args) {
//        printClazz(List.class);
//
//        printClazz(MYMYList.class);
//        printClazz(MYMYList.class.getSuperclass());
//        printParameterizedType(MYMYList.class.getGenericSuperclass());
        Type type = MYMYList.class.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
        }


//        printGenerateInterfaces(List.class);
//        System.out.println("----------------------");
//        printGenerateInterfaces(MYMYList.class);
//        System.out.println("----------------------");
//        printGenerateInterfaces(MYMYList.class.getSuperclass());
    }

    private static void printClazz(Class clazz) {
        TypeVariable[] typeParameters = clazz.getClass().getTypeParameters();
        for (TypeVariable typeVariable : typeParameters) {
            System.out.println("Class: " + clazz.getName() + " --- typeVariable: " + typeVariable);
            if (typeVariable instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) typeVariable;
                System.out.println("    " + "parameterizedType: " + parameterizedType);
            }
        }
    }

    private static void printParameterizedType(Type type) {
        ParameterizedType parameterizedType = (ParameterizedType) type;
        System.out.println("parameterizedType: " + parameterizedType +
                "\nRawType:" + parameterizedType.getRawType()
                + "\nOwnerType:" + parameterizedType.getOwnerType()
                +"\nTypeName:" + parameterizedType.getTypeName());
        Type[] types = parameterizedType.getActualTypeArguments();
        for (Type act : types) {
            System.out.println("\nActualTypeArgument: " + act);
        }
    }

    private static void printGenerateInterfaces(Class clazz) {
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        for (Type type : genericInterfaces) {
            if (type instanceof TypeVariable) {
                System.out.println("******TypeVariable: " + type);

            } else if (type instanceof ParameterizedType) {
                printParameterizedType(type);
            }
        }
    }

    private abstract static class MyList<T> implements List<T> {
    }

    private static class MYMYList extends MyList<String> {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<String> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(String s) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends String> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends String> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public String get(int index) {
            return null;
        }

        @Override
        public String set(int index, String element) {
            return null;
        }

        @Override
        public void add(int index, String element) {

        }

        @Override
        public String remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<String> listIterator() {
            return null;
        }

        @Override
        public ListIterator<String> listIterator(int index) {
            return null;
        }

        @Override
        public List<String> subList(int fromIndex, int toIndex) {
            return null;
        }
    }

}

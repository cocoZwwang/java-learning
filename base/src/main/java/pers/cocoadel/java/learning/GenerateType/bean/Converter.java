package pers.cocoadel.java.learning.GenerateType.bean;

public interface Converter<T> {
    T converter(String s);
}
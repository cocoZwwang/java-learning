package pers.cocoadel.java.learning.io;

import pers.cocoadel.java.learning.io.domain.Employee;

/**
 * 使用对象序列化深度克隆 测试
 */
public class SerialCloneableDemo {

    public static void main(String[] args) throws CloneNotSupportedException {
        Employee ruby = new Employee("ruby", 100000, 2021, 5, 29);
        Employee weiss = (Employee) ruby.clone();
        weiss.setName("weiss");
        System.out.println("ruby = " + ruby);
        System.out.println("weiss = " + weiss);
    }
}

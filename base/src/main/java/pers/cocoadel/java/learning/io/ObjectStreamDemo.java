package pers.cocoadel.java.learning.io;

import pers.cocoadel.java.learning.io.domain.Employee;
import pers.cocoadel.java.learning.io.domain.Manager;

import java.io.*;

public class ObjectStreamDemo {

    public static void main(String[] args) {
        Employee[] employees = new Employee[3];
        Employee ruby = new Employee("ruby",100000,2021,5,29);
        Manager weiss = new Manager("weiss",100000,2021,5,29);
        Manager yang = new Manager("yang",100000,2021,5,29);

        weiss.setSecretary(ruby);
        yang.setSecretary(ruby);

        employees[0] = weiss;
        employees[1] = ruby;
        employees[2] = yang;


        //序列化
        String path = "objects.txt";
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path))) {
            os.writeObject(employees);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //反序列化
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            Employee[] arr = ((Employee[]) ois.readObject());
            arr[1].raiseSalary(1000);
            for (Employee employee : arr) {
                System.out.println(employee);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

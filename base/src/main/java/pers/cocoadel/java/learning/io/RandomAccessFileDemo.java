package pers.cocoadel.java.learning.io;


import pers.cocoadel.java.learning.io.domain.Employee;

import java.io.*;
import java.time.LocalDate;

public class RandomAccessFileDemo {
    /**
     * 1、将三条长度相等的数据依次写入文件
     * 2、再按照倒序将每条数据读出来
     */
    public static void main(String[] args) {
        //名字 40 个字符：80 个字节
        int nameSize = 40;
        //薪水 double：8 个字节
        //年月日 3 个 int: 12 个字节
        int employeeSize = nameSize * 2 + 8 + 12;
        Employee[] employees = new Employee[3];
        employees[0] = new Employee("ruby", 100000, 2021, 8, 15);
        employees[1] = new Employee("weiss", 100000, 2021, 8, 16);
        employees[2] = new Employee("cocoAdel", 35000, 2021, 8, 17);
        String path = "employee.txt";
        //按照顺序写入文件
        try (DataOutputStream dataOutput = new DataOutputStream(new FileOutputStream(path))){
            for (Employee employee : employees) {
                DataIO.writeFixedString(employee.getName(), nameSize, dataOutput);
                dataOutput.writeDouble(employee.getSalary());
                LocalDate localDate = employee.getHireDay();
                dataOutput.writeInt(localDate.getYear());
                dataOutput.writeInt(localDate.getMonthValue());
                dataOutput.writeInt(localDate.getDayOfMonth());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //按照倒序读入数据
        try (RandomAccessFile in = new RandomAccessFile(path, "r")){
            int n = (int) (in.length() / employeeSize);
            System.out.println("in length: " + in.length() + " employee size: " + employeeSize);
            Employee[] newEmployees = new Employee[n];
            for(int i = n - 1; i >= 0; i--){
                //指定文件读取的指针位置
                in.seek((long) i * employeeSize);
                String name = DataIO.readFixedString(nameSize, in);
                double salary = in.readDouble();
                int year = in.readInt();
                int month = in.readInt();
                int dayOfMonth = in.readInt();
                newEmployees[n - 1 - i] = new Employee(name, salary, year, month, dayOfMonth);
            }
            //打印新数组
            for (Employee e : newEmployees) {
                System.out.println(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

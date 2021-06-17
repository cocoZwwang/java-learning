package pers.cocoadel.java.learning.io;

import pers.cocoadel.java.learning.io.domain.Employee;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Scanner;

public class ReaderAndWriterDemo {
    public static void main(String[] args) {
        Employee[] employees = new Employee[3];
        employees[0] = new Employee("ruby", 100000, 2021, 8, 15);
        employees[1] = new Employee("weiss", 100000, 2021, 8, 16);
        employees[2] = new Employee("cocoAdel", 35000, 2021, 8, 17);
        String path = "employee.bat";
        try (PrintWriter printWriter = new PrintWriter(path, StandardCharsets.UTF_8.name())){
            writeAllData(employees, printWriter);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try (Scanner scanner = new Scanner(new FileInputStream(path),StandardCharsets.UTF_8.name())){
            Employee[] arr = readAllData(scanner);
            for (Employee employee : arr) {
                System.out.println(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeAllData(Employee[] employees, PrintWriter printWriter) {
        int n = employees.length;
        printWriter.println(n);
        for (Employee employee : employees) {
            writeData(employee, printWriter);
        }
    }

    private static void writeData(Employee employee, PrintWriter printWriter) {
        printWriter.println(employee.getName() + "|" + employee.getSalary() + "|" + employee.getHireDay());
    }

    private static Employee[] readAllData(Scanner scanner) {
        int n = scanner.nextInt();
        scanner.nextLine();

        Employee[] employees = new Employee[n];
        for (int i = 0; i < n; i++) {
            employees[i] = readData(scanner);
        }
        return employees;
    }

    private static Employee readData(Scanner scanner) {
        String line = scanner.nextLine();
        String[] tokens = line.split("\\|");
        String name = tokens[0];
        double salary = Double.parseDouble(tokens[1]);
        LocalDate localDate = LocalDate.parse(tokens[2]);
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int dayOfMonth = localDate.getDayOfMonth();
        return new Employee(name, salary, year, month, dayOfMonth);
    }
}

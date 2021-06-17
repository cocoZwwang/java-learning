package pers.cocoadel.java.learning.io.domain;


import pers.cocoadel.java.learning.io.SerialCloneable;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDate;


public class Employee extends SerialCloneable implements Serializable{
    private static final long serialVersionUID = 5518219535361149931L;
    protected String name;
    protected double salary;
    protected LocalDate hireDay;

    public Employee() {

    }

    public Employee(String name, double salary, int year, int month, int dayOfMonth) {
        this.name = name;
        this.salary = salary;
        this.hireDay = LocalDate.of(year, month, dayOfMonth);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public LocalDate getHireDay() {
        return hireDay;
    }

    public void setHireDay(LocalDate hireDay) {
        this.hireDay = hireDay;
    }

    public void raiseSalary(int raise) {
        this.salary += raise;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", hireDay=" + hireDay +
                '}';
    }

    private Object writeReplace() {
        return new SerializationProxy(this);
    }

    private Object readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw  new InvalidObjectException("proxy required");
    }

    /**
     * 通过序列化代理来进行 jdk 序列化
     */
    protected static class SerializationProxy implements Serializable {

        private static final long serialVersionUID = 389057837773355841L;
        private String name;
        private double salary;
        private LocalDate hireDay;

        SerializationProxy(Employee employee) {
            this.name = employee.name;
            this.salary = employee.salary;
            this.hireDay = employee.hireDay;
        }

        protected Object readResolve() {
            int year = hireDay.getYear();
            int month = hireDay.getMonthValue();
            int dayOfMonth = hireDay.getDayOfMonth();
            return new Employee(name +" girl", salary, year, month, dayOfMonth);
        }
    }
}

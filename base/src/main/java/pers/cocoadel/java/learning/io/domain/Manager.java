package pers.cocoadel.java.learning.io.domain;


import java.io.InvalidObjectException;
import java.io.ObjectInputStream;

public class Manager extends Employee {

    private static final long serialVersionUID = 7728729442796256843L;

    public Manager(String name, double salary, int year, int month, int dayOfMonth) {
        super(name, salary, year, month, dayOfMonth);
    }

    private Manager(Employee src){
        this(src.getName(), src.getSalary(),
                src.getHireDay().getYear(), src.getHireDay().getMonthValue(), src.getHireDay().getDayOfMonth());
    }

    private Employee secretary;

    public Employee getSecretary() {
        return secretary;
    }

    public void setSecretary(Employee secretary) {
        this.secretary = secretary;
    }

    @Override
    public String toString() {
        return "Manager{" +
                super.toString()+
                ", secretary=" + secretary +
                '}';
    }

    private Object writeReplace() {
        return new SubSerializationProxy(this);
    }

    private Object readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("serial proxy required");
    }

    /**
     * 子类不会走父类的序列化代理
     */
    private static class SubSerializationProxy extends SerializationProxy{
        private static final long serialVersionUID = 2394631797335815548L;
        private Employee secretary;

        SubSerializationProxy(Manager manager) {
            super(manager);
            this.secretary = manager.getSecretary();
        }

        protected Object readResolve() {
            Object value = super.readResolve();
            Manager manager = new Manager((Employee) value);
            manager.setSecretary(secretary);
            return manager;
        }
    }
}

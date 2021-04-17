package per.cocoadel.learing.jvm.classloader;

public class MethodOverloadDemo {
    static abstract class Human{

    }

    static class Man extends Human {

    }

    static class Woman extends Human {
    }


    private void sayHello(Human human) {
        System.out.println("hello guy!");
    }

    private void sayHello(Man man) {
        System.out.println("hello man");

    }

    private void sayHello(Woman woman) {
        System.out.println("hello woman!");
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();

        MethodOverloadDemo demo = new MethodOverloadDemo();

        demo.sayHello(man);
        demo.sayHello(woman);

        man = new Man();
        man = new Woman();
//        demo.sayHello((Man) man);
        demo.sayHello((Woman) man);
    }
}

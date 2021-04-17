package per.cocoadel.learing.jvm.classloader;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

public class InvokeDynamicDemo {

    class GrandFather {
        public void think() {
            System.out.println("GrandFather");
        }
    }


    class Father extends GrandFather {
        public void think() {
            System.out.println("Father");
        }
    }

    class Son extends Father {
        public void think() {
//            GrandFather grandFather = this;
//            grandFather.think();

            //1.7可以
//            System.out.println("==============================");
//            try {
//                MethodType methodType = MethodType.methodType(void.class);
//                MethodHandle methodHandle =
//                        MethodHandles.lookup().findSpecial(GrandFather.class, "think", methodType, getClass());
//                methodHandle.invoke(this);
//            } catch (Throwable throwable) {
//                throwable.printStackTrace();
//            }


            //1.8 对调用者敏感
            //需要创建method handles的查找类将调用MethodHandles.lookup为它自己创建一个工厂。
            //当该工厂对象被查找类创建后，查找类的标识，安全信息将存储在其中。
            //查找类(或它的委托)将使用工厂方法在被查找对象上依据查找类的访问限制，创建method handles。
            //可创建的方法包括：查找类所有允许访问的所有方法、构造函数和字段，甚至是私有方法。
            try {
                MethodType methodType = MethodType.methodType(void.class);
                Field field = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
                field.setAccessible(true);
                MethodHandles.Lookup lookup = (MethodHandles.Lookup) field.get(null);
                MethodHandle mh = lookup.findSpecial(GrandFather.class, "think", methodType, GrandFather.class);
                mh.invoke(this);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        (new InvokeDynamicDemo().new Son()).think();
    }
}

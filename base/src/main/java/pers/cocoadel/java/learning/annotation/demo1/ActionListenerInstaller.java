package pers.cocoadel.java.learning.annotation.demo1;

import java.awt.event.ActionListener;
import java.lang.reflect.*;

public class ActionListenerInstaller {

    public static void processAnnotation(Object object) {
        try {
            Method[] methods = object.getClass().getDeclaredMethods();
            for (Method method : methods) {
                ActionListenerFor annotation = method.getAnnotation(ActionListenerFor.class);
                if (annotation != null) {
                    String source = annotation.source();
                    Field field = object.getClass().getDeclaredField(source);
                    field.setAccessible(true);
                    addListener(field.get(object),object,method);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addListener(Object source, Object object, Method method) throws Exception {
        InvocationHandler invocationHandler = (proxy, method1, args) -> method.invoke(object);

        ActionListener listener = (ActionListener) Proxy.newProxyInstance(null, new Class[]{ActionListener.class}, invocationHandler);
        Method adder = source.getClass().getMethod("addActionListener",ActionListener.class);
        adder.invoke(source, listener);
    }
}

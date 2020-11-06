package per.cocoadel.learing.jvm.memory.error;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 方法区内存溢出测试
 * 通过CGLIB动态往方法区添加动态类
 * VM args: -XX:PermSize=10m -XX:MaxPermSize=10M
 */
public class JavaMethodAreaOOM {
    public static void main(final String[] args) {
        while(true){
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invokeSuper(o,args);
                }
            });
            enhancer.create();
        }
    }

    public static class OOMObject{

    }
}

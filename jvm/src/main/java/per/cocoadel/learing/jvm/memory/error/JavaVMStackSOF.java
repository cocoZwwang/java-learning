package per.cocoadel.learing.jvm.memory.error;

/**
 * 虚拟机栈和本地方法溢出
 * 如果线程请求的栈深度大于虚拟机所允许的最大深度，将抛出StackOverFlowError异常
 * 如果虚拟机扩展栈时候，无法申请到足够的内存空间，将抛出OutOfMemoryError异常
 *  -Xss128k
 *  由于HotSpot虚拟机本地方法栈和虚拟机栈是不区分的，因此虽然对于HotSpot来说，-Xoss参数虽然存在，但是是无效的，栈容量只有-Xss参数决定
 */
public class JavaVMStackSOF {
    private int stackLength = 1;

    /**
     * 递归方法不断创建栈帧
     */
    public void stackLeak(){
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) throws Throwable {
        JavaVMStackSOF javaVMStackSOF = new JavaVMStackSOF();
        try {
            javaVMStackSOF.stackLeak();
        }catch (Throwable e){
            System.out.println("stack leak: " + javaVMStackSOF.stackLength);
            throw e;
        }
    }
}

package pers.cocoadel.leanring.nio.buffer;

import java.nio.IntBuffer;

public class BufferDemo {
    public static void main(String[] args) {
        int capacity = 5;
        IntBuffer intBuffer = IntBuffer.allocate(capacity);
        for(int i = 0; i < capacity;i++){
            intBuffer.put(i * 2);
        }

        //写模型转换成读模型
        intBuffer.flip();
        //可以读的个数
//        intBuffer.limit(2);
        int size = intBuffer.remaining();
        for(int i = 0; i < size;i++){
            System.out.println(intBuffer.get());
        }
    }
}

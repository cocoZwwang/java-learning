package pers.cocoadel.learning.algorithm.stack;

import pers.cocoadel.learning.algorithm.array.MyArrayList;

/**
 * 基于动态扩容数组的栈
 */
public class MyArrayStack implements IStack<Integer> {

    private final MyArrayList<Integer> arrayList;

    public MyArrayStack(){
        arrayList = new MyArrayList<>();
    }


    @Override
    public void push(Integer value) {
        //添加元素到表尾部
        arrayList.add(value);
    }

    @Override
    public Integer pop() {
        //删除表尾部元素
        return arrayList.remove(arrayList.size() - 1);
    }

    @Override
    public Integer peek() {
        return arrayList.get(arrayList.size() - 1);
    }

    @Override
    public int size() {
        return arrayList.size();
    }

    @Override
    public boolean isEmpty() {
        return arrayList.isEmpty();
    }

    @Override
    public boolean isFull() {
        return false;
    }
}

package pers.cocoadel.learning.algorithm.collection;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class JosePushDemo {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        pass(3,10);
        StringBuilder sb = new StringBuilder();
        sb.reverse();
        System.out.println("time: " + (System.currentTimeMillis() - start));
    }

    private static void pass(int m , int n){
        int mPrime = 0;
        int numLeft = n;

//        List<Integer> list = new ArrayList<>();
        List<Integer> list = new LinkedList<>();
        for(int i = 1; i <= n; i++){
            list.add(i);
        }

        ListIterator<Integer> iterator = list.listIterator();
        Integer item = 0;
        for(int i = 0; i < n; i++){
            mPrime = m % numLeft;
            if(mPrime <= numLeft / 2){
                //迭代器 next 后会删除其左边的元素
                //所以传递 0 次，则是 next 一次后 rmeove
                for(int j = 0; j <= mPrime; j++){
                    //如果一圈已经走完，继续循环从头开始
                    if(!iterator.hasNext()){
                        iterator = list.listIterator();
                    }
                    item = iterator.next();
                }
            }else {
                for(int j = 0; j < numLeft - mPrime; j++){
                    //如果一圈已经走完，则循环到尾部
                    if(!iterator.hasPrevious()){
                        iterator = list.listIterator(list.size());
                    }
                    item = iterator.previous();
                }
            }
            System.out.println("mPrime: " + mPrime +" remove item: " + item);
            iterator.remove();
            System.out.println(list);
            numLeft--;
        }
    }
}

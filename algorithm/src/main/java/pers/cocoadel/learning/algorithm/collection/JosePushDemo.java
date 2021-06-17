package pers.cocoadel.learning.algorithm.collection;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.ListIterator;

public class JosePushDemo {
    public static void main(String[] args) {
        Instant start = Instant.now();
        pass(3,10);
        System.out.println("time: " + Duration.between(start, Instant.now()).toMillis());
    }

    private static void pass(int m, int n){
        int numLeft = n;
        int numPrime = 0;
        LinkedList<Integer> list = new LinkedList<>();
        for(int i = 1; i <= n; i++){
            list.add(i);
        }

        ListIterator<Integer> iterator = list.listIterator();
        Integer item = 0;
        for(int i = 0; i < n; i++){
            numPrime = m % numLeft;
            if(numPrime <= numLeft / 2){
                for(int j = 0; j <= numPrime; j++){
                    if (!iterator.hasNext()) {
                        iterator = list.listIterator();
                    }
                    item = iterator.next();
                }
            }else{
                for(int j = 0; j < numLeft - numPrime; j++){
                    if (iterator.hasPrevious()) {
                        iterator = list.listIterator(list.size());
                    }
                    item = iterator.previous();
                }
            }
            System.out.println("numPrime: " + numPrime + " remove item: " + item);
            iterator.remove();
            System.out.println(list);
            numLeft--;
        }

    }
}

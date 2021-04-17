package pers.cocoadel.learning.algorithm.collection;

import java.util.*;

public class LinkedListTest {

    public static void main(String[] args) {
        LinkedList<String> a = new LinkedList<>();
        a.add("Amy");
        a.add("Carl");
        a.add("Erica");

        LinkedList<String> b = new LinkedList<>();
        b.add("Bod");
        b.add("Doug");
        b.add("Frances");
        b.add("Gloria");

        ListIterator<String> aIterator = a.listIterator();
        Iterator<String> bIterator = b.iterator();
        while (bIterator.hasNext()) {
            if (aIterator.hasNext()) {
                aIterator.next();
            }
            aIterator.add(bIterator.next());
        }
        // Amy Bod Carl Doug Erica Frances Gloria
        System.out.println(a);


        bIterator = b.iterator();
        while (bIterator.hasNext()) {
            bIterator.next();
            if (bIterator.hasNext()) {
                bIterator.next();
                bIterator.remove();
            }
        }
        //Bod Frances
        System.out.println(b);

        a.removeAll(b);
        //Amy Carl Doug Erica Gloria
        System.out.println(a);
    }
}

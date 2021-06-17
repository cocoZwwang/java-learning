package pers.cocoadel.learning.algorithm.sort.list;

import java.util.ArrayList;
import java.util.List;

public class ArrayListQuickSort {

    public void quickSort(List<Integer> list) {
        if (list.size() > 0) {
            List<Integer> smaller = new ArrayList<>();
            List<Integer> same = new ArrayList<>();
            List<Integer> larger = new ArrayList<>();
            Integer pivot = list.get(list.size() / 2);
            for (Integer i : list) {
                if (i > pivot) {
                    larger.add(i);
                } else if (i < pivot) {
                    smaller.add(i);
                }else{
                    same.add(i);
                }
            }
            quickSort(smaller);
            quickSort(larger);
            list.clear();
            list.addAll(smaller);
            list.addAll(same);
            list.addAll(larger);
        }
    }
}

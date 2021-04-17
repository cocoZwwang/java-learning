package pers.cocoadel.learning.algorithm.sort;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class QuickSort implements Sort {

    private final Map<QuickSortStrategy, AbstractQuickSort> sortMap = new HashMap<>();

    private QuickSortStrategy defaultStrategy = QuickSortStrategy.BASE;

    public QuickSort() {
        sortMap.put(QuickSortStrategy.BASE, new BaseQuickSort());
        sortMap.put(QuickSortStrategy.POINT_COLLISION, new PointCollisionQuickSort());
        sortMap.put(QuickSortStrategy.THREE_PARTITION, new ThreePartitionQuickSort());
    }

    public void setDefaultStrategy(QuickSortStrategy defaultStrategy) {
        this.defaultStrategy = defaultStrategy;
    }

    @Override
    public void sort(int[] array) {
        sort(array, defaultStrategy);
    }

    public void sort(int[] array, QuickSortStrategy sortStrategy) {
        sortMap.get(sortStrategy).sort(array);
    }

    protected static abstract class AbstractQuickSort implements Sort {
        @Override
        public void sort(int[] array) {
            if (array == null || array.length <= 1) {
                return;
            }
            quickSort(array, 0, array.length - 1);
        }

        protected void quickSort(int[] array, int left, int right) {
            if (left >= right) {
                return;
            }
            int p = partition(array, left, right);
            quickSort(array, left, p - 1);
            quickSort(array, p + 1, right);
        }

        protected abstract int partition(int[] array, int left, int right);
    }

    /**
     * 基本快速排序
     */
    protected static class BaseQuickSort extends AbstractQuickSort {

        private final static Random RANDOM = new Random();

        protected int partition(int[] array, int left, int right) {
            int pivotIndex = selectPivot(array, left, right);
            swap(array, pivotIndex, left);

            int pivot = array[left];
            int j = left;
            for (int i = left + 1; i <= right; i++) {
                if (array[i] < pivot) {
                    swap(array, i, ++j);
                }
            }
            swap(array, left, j);
            return j;
        }

        protected int selectPivot(int[] array, int left, int right) {
            //随机找一个元素作为基准点
            return left + RANDOM.nextInt(right - left + 1);
        }
    }

    /**
     * 双指针碰撞 快速排序
     */
    private static class PointCollisionQuickSort extends BaseQuickSort {

        @Override
        protected int partition(int[] array, int left, int right) {
            int pivotIndex = selectPivot(array, left, right);
            swap(array, pivotIndex, left);

            int pivot = array[left];
            int i = left + 1;
            int j = right;
            while (true) {
                while (i <= j && array[i] < pivot) {
                    i++;
                }

                while (i <= j && array[j] > pivot) {
                    j--;
                }
                if (i > j) {
                    break;
                }
                swap(array, i, j);
                i++;
                j--;
            }
            swap(array, left, j);
            return j;
        }
    }

    /**
     * 三向切分的快速排序
     */
    private static class ThreePartitionQuickSort extends BaseQuickSort {

        @Override
        protected void quickSort(int[] array, int left, int right) {
            if (left >= right) {
                return;
            }
            int[] p = threePartition(array, left, right);
            quickSort(array, left, p[0] - 1);
            quickSort(array, p[1], right);
        }

        protected int[] threePartition(int[] array, int left, int right) {
            int pivotIndex = selectPivot(array, left, right);
            swap(array,left,pivotIndex);

            int pivot = array[left];
            int lt = left;
            int gt = right + 1;
            for (int i = left + 1; i < gt; ) {
                if (pivot == array[i]) {
                    i++;
                } else if (array[i] < pivot) {
                    swap(array, i++, ++lt);
                } else {
                    swap(array, i, --gt);
                }
            }
            swap(array, left, lt);
            return new int[]{lt,gt};
        }
    }


    public enum QuickSortStrategy {
        BASE, POINT_COLLISION,THREE_PARTITION;
    }
}

package pers.cocoadel.learning.algorithm.heap;

import java.util.NoSuchElementException;

/**
 * 二叉堆（大根堆）实现
 */
public class BinaryHeap implements Heap<Integer>{
    //当前堆的元素个数
    private int size;
    //实现二叉堆的数组
    private final int[] arr;
    //表示树的分叉数量，这里是二叉堆，所以d=2
    private final int d;

    //maxSize表示堆的最大容量
    public BinaryHeap(int maxSize){
        if(maxSize < 0){
            throw  new IllegalArgumentException("the max size must be >= 0");
        }
        size = 0;
        arr = new int[maxSize];
        d = 2;
    }

    @Override
    public Integer peek() throws NoSuchElementException{
        if(isEmpty()){
            throw new NoSuchElementException("the heap is empty");
        }
        //直接返回数组第一个元素即可
        return arr[0];
    }

    @Override
    public Integer poll() {
        //直接删除数组第一个元素
        return delete(0);
    }

    @Override
    public Integer delete(int i) throws NoSuchElementException{
        //检查下标i是否符合要求
        if(i >= size || i < 0){
            throw new NoSuchElementException("the heap is empty");
        }
        //获取当前需要删除元素
        int res = arr[i];
        //把堆尾元素赋值到当前位置
        arr[i] = arr[size - 1];
        size--;
        //依次从顶部向下调整整个堆的结构（一直到堆尾即可）
        heapifyDown(0);
        return res;
    }

    @Override
    public void insert(Integer val) throws NoSuchElementException {
        //如果堆满了，抛出异常
        if(isFull()){
            throw  new NoSuchElementException("the heap is full");
        }
        //先把元素添加到二叉堆尾部，也就是数组的尾部
        arr[size] = val;
        //size+1
        size++;
        //依次向下调整整个堆的结构（直到叶子）
        heapifyUP(size - 1);
    }

    @Override
    public boolean isFull() {
        return size == arr.length;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    private void heapifyUP(int i){
        //需要插入的元素
        int insertValue = arr[i];
        //需要插入的元素和它当前的父结点比较，如果比父节点大，则交换位置，直到跟结点。
        while(i > 0 && insertValue > arr[parent(i)]){
            //把父结点的设置到当前位置
            arr[i] = arr[parent(i)];
            //i往上挪
            i = parent(i);
        }
        //赋值到最终调整的位置
        arr[i] = insertValue;
    }

    private void heapifyDown(int i){
        //获取需要向下调整的元素
        int value = arr[i];
        //循环直到叶子
        //判断：当前结点是否存在第一个孩子，如果不存在，表示当前已经是叶子
        while(kthChild(i,1) < size){
            int maxChild = maxChild(i,d);
            //如果当前节点的最大儿子比value小，则停止调整，当前位置就是value可以存在的位置
            if(value >= arr[maxChild]){
                break;
            }
            //否则把最大儿子往上提
            arr[i] = arr[maxChild];
            //当前节点索引下移
            i = maxChild;
        }
        //最后把value赋值到最终调整的位置
        arr[i] = value;
    }

    /**
     * 返回索引为parentIndex的元素，从第1~k个孩子中值最大的孩子的索引
     */
    private int maxChild(int parentIndex,int k){
        int res = -1;
        for(int i = 1; i <= k; i++){
            //第i个孩子的索引
            int index = kthChild(parentIndex,i);
            if(index >= size){
                break;
            }
            //比较值，获取较大的值的索引
            if(res == - 1 || arr[index] > arr[res]){
                res  = index;
            }
        }
        return res;
    }
    /**
     * 返回索引为i的元素的第K个左孩子的索引
     * 完全二叉树的话左孩子k = 1，右孩子k=2
     */
    private int kthChild(int i ,int k){
        return d * i + k;
    }

    /**
     * 索引为i的元素的父结点的索引是：floor((i - 1)/2)
     */
    private int parent(int i){
        return (i - 1) / d;
    }

    public void printHeap(){
        StringBuilder stringBuilder = new StringBuilder("Heap=[");
        for(int i = 0; i < size; i++){
            stringBuilder.append(arr[i]).append(',');
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append(']');
        System.out.println(stringBuilder);
    }

}

package pers.cocoadel.learning.algorithm.array;

/**
 * 带动态扩容和缩容的数组链表
 * @param <T>
 */
public class MyArrayList<T> {

    private Object[] array;
    private int size = 0;
    private int capacity;

    private final static int DEFAULT_CAPACITY = 10;

    public MyArrayList(int capacity) {
        this.capacity = capacity;
        array = new Object[this.capacity];

    }

    public MyArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * 在指定位置插入一个元素，如果插入时候数组已经满了，则扩容为原来的2倍
     */
    public void add(int index, T value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(String.format("Index %s out of bounds for length %s", index, size));
        }

        if (size == capacity) {
            newCapacity(2 * size);
        }

        //如果插入位置不是链表尾部，需要把index 后面的元素往后挪动一个位置
        if (size > index) {
            System.arraycopy(array, index, array, index + 1, size - index);
        }
        array[index] = value;
        size++;
    }

    /**
     * 在数组元素尾部添加一个元素
     */
    public void add(T value) {
        add(size,value);
    }

    /**
     * 修改指定下标的值
     */
    public void set(int index, T value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index %s out of bounds for length %s", index, size));
        }
        array[index] = value;
    }

    /**
     * 删除指定下标的元素，如果删除后数组长度是原来的 1/4 则缩容为原来的 1/2
     */
    public T remove(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(String.format("Index %s out of bounds for length %s", index, size));
        }
        T res = (T) array[index];
        //如果不是最后一个元素需要把后面的元素往前挪
        if(index < size - 1){
            System.arraycopy(array,index + 1,array,index,size - index - 1);
        }
        array[size - 1] = null;
        size--;
        //如果元素个数是容量的 1/4 则缩容 1/2，防止复杂度震荡
        if (size == capacity / 4) {
            newCapacity(capacity / 2);
        }
        return res;
    }

    /**
     * 指定元素如果存在，则删除
     */
    public void remove(T value) {
        int index = indexOf(value);
        if (index != -1) {
            remove(index);
        }
    }

    /**
     * 指定元素如果存在则返回其下标，否则返回 -1
     */
    public int indexOf(T value) {
        for (int i = 0; i < size; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 数组是否包含指定元素
     */
    public boolean contains(T value) {
        for (int i = 0; i < size; i++) {
            if (array[i] == value) {
                return true;
            }
        }
        return false;
    }

    /**
     * 数组是否为空
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 当前元素个数
     */
    public int size() {
        return size;
    }

    /**
     * 当前数组容量
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * 读取指定下标的元素
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index %s out of bounds for length %s", index, size));
        }
        return (T) array[index];
    }

    /**
     * 动态扩容或者缩容
     * @param capacity 新的数组容量
     */
    private void newCapacity(int capacity) {
        this.capacity = capacity;
        Object[] newArray = new Object[capacity];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

}

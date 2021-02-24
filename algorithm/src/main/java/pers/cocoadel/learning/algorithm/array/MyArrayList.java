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

    public void add(T value) {
        add(size,value);
    }

    public void set(int index, T value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index %s out of bounds for length %s", index, size));
        }
        array[index] = value;
    }

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

    public void remove(T value) {
        int index = indexOf(value);
        if (index != -1) {
            remove(index);
        }
    }

    public int indexOf(T value) {
        for (int i = 0; i < size; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public boolean contains(T value) {
        for (int i = 0; i < size; i++) {
            if (array[i] == value) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index %s out of bounds for length %s", index, size));
        }
        return (T) array[index];
    }

    private void newCapacity(int capacity) {
        this.capacity = capacity;
        Object[] newArray = new Object[capacity];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

}

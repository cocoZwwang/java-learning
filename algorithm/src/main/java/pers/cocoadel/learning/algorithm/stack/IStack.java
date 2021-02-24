package pers.cocoadel.learning.algorithm.stack;

public interface IStack<T> {

   void push(T value);

   T pop();

   T peek();

   int size();

   boolean isEmpty();

   boolean isFull();

}

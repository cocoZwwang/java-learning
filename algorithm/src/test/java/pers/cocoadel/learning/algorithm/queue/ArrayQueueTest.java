package pers.cocoadel.learning.algorithm.queue;

import org.junit.Test;

import static org.junit.Assert.*;


public class ArrayQueueTest {


    @Test
    public void offerAndPeekTest() {
        IQueue<Integer> queue = new ArrayQueue(10);
        queue.offer(1);
        assertEquals(1, (int) queue.peek());
        queue.offer(2);
        assertEquals(1, (int) queue.peek());
    }

    @Test
    public void offerAndPoll() {

        IQueue<Integer> queue = new ArrayQueue(10);
        queue.offer(1);
        queue.offer(2);

        assertEquals(1, (int) queue.poll());

        queue.offer(3);

        assertEquals(2, (int) queue.poll());
        assertEquals(3, (int) queue.poll());
    }

    @Test
    public void isEmpty() {
        IQueue<Integer> queue = new ArrayQueue(10);
        assertTrue(queue.isEmpty());

        queue.offer(1);
        queue.offer(2);
        assertFalse(queue.isEmpty());

        queue.poll();
        queue.poll();
        assertTrue(queue.isEmpty());

    }


    @Test
    public void isFullTest() {
        IQueue<Integer> queue = new ArrayQueue(10);
        assertFalse(queue.isFull());

        for(int i = 0; i < 10; i++){
            queue.offer(i);
        }

        assertTrue(queue.isFull());
    }

}
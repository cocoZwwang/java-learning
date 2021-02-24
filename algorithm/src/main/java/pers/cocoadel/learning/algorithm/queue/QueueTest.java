package pers.cocoadel.learning.algorithm.queue;

import static com.google.common.base.Preconditions.checkArgument;

public class QueueTest {
    public static void main(String[] args) {

        IQueue<Integer> queue = createIQueue();
        simpleTest(queue);
        circleTest(queue);
        circleDequeTest(queue);
    }

    private static void simpleTest(IQueue<Integer> queue) {
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        queue.offer(5);

        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());

        System.out.println("------------------------------------");

        queue.offer(6);
        queue.offer(7);

        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }

    private static void circleTest(IQueue<Integer> queue) {
        if (!(queue instanceof CircleIQueue)) {
            return;
        }
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                queue.offer(j * 10 + i);
            }
            while(!queue.isEmpty()){
                System.out.println(queue.poll());
            }
        }
    }

    /**
     * MyCircularDeque circularDeque = new MycircularDeque(3); // 设置容量大小为3
     * circularDeque.insertLast(1);			        // 返回 true
     * circularDeque.insertLast(2);			        // 返回 true
     * circularDeque.insertFront(3);			        // 返回 true
     * circularDeque.insertFront(4);			        // 已经满了，返回 false
     * circularDeque.getRear();  				// 返回 2
     * circularDeque.isFull();				        // 返回 true
     * circularDeque.deleteLast();			        // 返回 true
     * circularDeque.insertFront(4);			        // 返回 true
     * circularDeque.getFront();				// 返回 4
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/design-circular-deque
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param queue
     */
    private static void circleDequeTest(IQueue<Integer> queue) {
        if(queue instanceof CircleDeque){
            CircleDeque<Integer> circularDeque = (CircleDeque<Integer>) queue;
            checkArgument(circularDeque.insertLast(1));	        // 返回 true
            checkArgument(circularDeque.insertLast(2));		        // 返回 true
            checkArgument(circularDeque.insertFront(3));		        // 返回 true
            checkArgument(!circularDeque.insertFront(4));	        // 已经满了，返回 false
            checkArgument(circularDeque.rear() == 2);				// 返回 2
            checkArgument(circularDeque.isFull());			        // 返回 true
            checkArgument(circularDeque.deleteLast());	        // 返回 true
            checkArgument(circularDeque.insertFront(4));		        // 返回 true
            checkArgument(circularDeque.front() == 4);				// 返回 4
            System.out.println("circleDequeTest 测试通过！！！");
        }
    }

    private static IQueue<Integer> createIQueue() {
        return new StackQueue();
//        return new ArrayQueue(10);
//        return new ArrayCircleQueue(10);
//        return new ArrayCircleDeque(3);
//        return new ArrayCircleDeque2(3);
    }

}

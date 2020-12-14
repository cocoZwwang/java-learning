package pers.cocoadel.learning.netty4.util;

import java.util.concurrent.atomic.AtomicLong;

public class IdUtil {
    private static final AtomicLong ATOMIC_LONG = new AtomicLong();

    private IdUtil(){

    }

    public static long next(){
        return ATOMIC_LONG.incrementAndGet();
    }
}

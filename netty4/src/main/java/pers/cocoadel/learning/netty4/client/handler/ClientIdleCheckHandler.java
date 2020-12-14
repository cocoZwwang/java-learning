package pers.cocoadel.learning.netty4.client.handler;

import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class ClientIdleCheckHandler extends IdleStateHandler {
    //如果间隔5秒中没有任何的数据传输，就会发生一次idle Event
    public ClientIdleCheckHandler() {
        super(0, 5, 0, TimeUnit.SECONDS);
    }
}

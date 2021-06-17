package pers.cocoadel.java.learning.redis.lettuce.subscriber;

import io.lettuce.core.pubsub.RedisPubSubListener;

public class MyListener implements RedisPubSubListener<String, String> {


    @Override
    public void message(String s, String o) {
        System.out.printf("channel: %s message: %s%n",s,o);
    }

    @Override
    public void message(String s, String k1, String o) {
        System.out.printf("pattern: %s channel: %s message: %s%n",s,k1,o);
    }

    @Override
    public void subscribed(String s, long l) {
        System.out.printf("subscribed, channel = %s, currentChannelCount = %s", s, l);
    }

    @Override
    public void psubscribed(String s, long l) {
        System.out.printf("psubscribed, channel = %s, currentChannelCount = %s", s, l);
    }

    @Override
    public void unsubscribed(String s, long l) {
        System.out.printf("unsubscribed, channel = %s, currentChannelCount = %s", s, l);
    }

    @Override
    public void punsubscribed(String s, long l) {
        System.out.printf("punsubscribed, channel = %s, currentChannelCount = %s", s, l);
    }
}

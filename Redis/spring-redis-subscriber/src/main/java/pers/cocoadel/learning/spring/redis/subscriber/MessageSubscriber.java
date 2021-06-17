package pers.cocoadel.learning.spring.redis.subscriber;

import org.springframework.stereotype.Component;
import pers.cocoadel.learning.spring.redis.domain.SimpleMessage;

@Component
public class MessageSubscriber {

    public void onMessage(SimpleMessage message, String pattern) {
        System.out.printf("topic=%s received=%s \n", pattern, message.toString());
    }
}

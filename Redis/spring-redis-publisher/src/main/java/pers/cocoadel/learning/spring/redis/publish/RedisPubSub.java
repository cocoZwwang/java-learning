package pers.cocoadel.learning.spring.redis.publish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;
import pers.cocoadel.learning.spring.redis.domain.SimpleMessage;

import java.util.Date;

@Component
public class RedisPubSub {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private ChannelTopic topic = new ChannelTopic("/redis/pubsub");

    /**
     * 推送消息
     */
    public void publish(String publisher, String content) {
        SimpleMessage pushMsg = new SimpleMessage();
        pushMsg.setContent(content);
        pushMsg.setCreateTime(new Date());
        pushMsg.setPublisher(publisher);
        redisTemplate.convertAndSend(topic.getTopic(), pushMsg);
    }
}

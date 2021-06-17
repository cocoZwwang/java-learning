package pers.cocoadel.java.learning.redis.lettuce.subscriber;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class SubscriberApp {
    public static void main(String[] args) {
        StatefulRedisPubSubConnection<String, String> redisConnection = null;
        RedisPubSubCommands<String, String> commands = null;
        try {
            RedisURI redisUri = RedisURI.builder()
                    .withHost("localhost")
                    .withPort(6379)
                    // 注意这里只能是0号库
                    .withDatabase(0)
                    .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
                    .build();
            RedisClient redisClient = RedisClient.create(redisUri);
            redisConnection = redisClient.connectPubSub();
            redisConnection.addListener(new MyListener());
            commands = redisConnection.sync();
            commands.subscribe("channel1");
            System.out.println("按任何键推出....");
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (redisConnection != null) {
                redisConnection.close();
            }
        }
    }
}

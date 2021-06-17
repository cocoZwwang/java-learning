package pers.cocoadel.java.learning.redis.lettuce.publisher;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class DemoApp {
    public static void main(String[] args) throws IOException {
        StatefulRedisConnection<String, String> redisConnection = null;
        RedisCommands<String, String> commands = null;
        try {
            RedisURI redisUri = RedisURI.builder()
                    .withHost("localhost")
                    .withPort(6379)
                    // 注意这里只能是0号库
                    .withDatabase(0)
                    .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
                    .build();
            RedisClient redisClient = RedisClient.create(redisUri);
            redisConnection = redisClient.connect();
            commands = redisConnection.sync();


            Scanner scanner = new Scanner(System.in);
            while (true){
                String line = scanner.nextLine();
                if("q".equalsIgnoreCase(line)){
                    break;
                }
                commands.publish("channel1", line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (redisConnection != null) {
                redisConnection.close();
            }
        }
    }
}

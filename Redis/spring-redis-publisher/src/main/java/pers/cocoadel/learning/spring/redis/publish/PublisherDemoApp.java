package pers.cocoadel.learning.spring.redis.publish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PublisherDemoApp {

    @Autowired
    private RedisPubSub redisPubSub;

    public static void main(String[] args) {
        SpringApplication.run(PublisherDemoApp.class, args);
    }

    @Bean
    public ApplicationRunner runner(){
        return (arguments) -> {
            for(int i = 0; i < 10; i++)
                redisPubSub.publish("cocoAdel","Hello!!");
        };
    }
}

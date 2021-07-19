package com.buit.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author yueyu
 * @Date 2020/7/10 10:05
 */
@Configuration
public class RedissonClientConfig {

    @Bean
    public RedissonClient redissonClient(RedisProperties prop){
        Config config = new Config();
        config.useSingleServer()
                .setAddress(String.format("redis://%s:%d",prop.getHost(),prop.getPort()))
                .setDatabase(prop.getDatabase())
                .setPingConnectionInterval(60000)
                .setKeepAlive(true)
//                .setTimeout(prop.getTimeout().toMillisPart())
                .setPassword(prop.getPassword());
        return Redisson.create(config);
    }
}

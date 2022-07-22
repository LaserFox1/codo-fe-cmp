package com.lkww.codo.codo.configuration;

import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
@EnableRedisDocumentRepositories(basePackages = "com.lkww.codo.codo.persistence")
public class RedisConfig {
    @Value("${output.url}")
    private String URL_OUT;

    @Value("${output.port}")
    private int PORT_OUT;

    @Bean
    public JedisConnectionFactory connectionFactory(){
        RedisStandaloneConfiguration conf = new RedisStandaloneConfiguration();
        conf.setHostName(URL_OUT);
        conf.setPort(PORT_OUT);
        return new JedisConnectionFactory(conf);
    }
}


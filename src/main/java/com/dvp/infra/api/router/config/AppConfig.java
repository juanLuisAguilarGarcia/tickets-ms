package com.dvp.infra.api.router.config;

import com.dvp.infra.api.router.RouterConsts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
@ComponentScan(RouterConsts.COMPONENT_SCAN)
public class AppConfig {

    @Value("${cache.jedis.config.url}")
    private String url;

    @Value("${cache.jedis.config.port}")
    private Integer port;

    @Value("${cache.jedis.config.password}")
    private String password;

    @Bean
    public Jedis getJedis() {
        Jedis jedis = new Jedis(url, port, true);
        jedis.auth(password);

        return jedis;
    }
}

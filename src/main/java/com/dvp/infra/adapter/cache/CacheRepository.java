package com.dvp.infra.adapter.cache;

import com.dvp.domain.port.cache.CachePortRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;


@Repository
public class CacheRepository implements CachePortRepository {

    @Autowired
    private Jedis jedis;

    @Value("${cache.jedis.config.ttl}")
    private Long ttl;

    @Transactional
    public String get(String key){
        return jedis.get(key);
    }

    public void set(String key, String value){
        jedis.setex(key, 60L, value);
        return;
    }
}

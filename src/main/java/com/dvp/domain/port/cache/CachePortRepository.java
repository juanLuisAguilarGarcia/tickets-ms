package com.dvp.domain.port.cache;

public interface CachePortRepository {
    String get(String key);
    void set(String key, String value);
}

package com.frameneo.infrastructure.cache.caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;

import java.util.concurrent.TimeUnit;

public class CaffeineCacheFactory {
    public Cache createCache(String name, long maxSize, long expireMinutes) {
        return new CaffeineCache(name, Caffeine.newBuilder()
        .maximumSize(maxSize)
        .expireAfterWrite(expireMinutes, TimeUnit.MINUTES)
        .build());
    }
}

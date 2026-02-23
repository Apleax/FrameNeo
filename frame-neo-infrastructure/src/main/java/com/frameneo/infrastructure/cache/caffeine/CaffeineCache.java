package com.frameneo.infrastructure.cache;

import org.springframework.cache.Cache;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class CaffeineCache extends Cache {
    /**
     * @return
     */
    @Override
    public String getName() {
        return "";
    }

    /**
     * @return
     */
    @Override
    public Object getNativeCache() {
        return null;
    }

    /**
     * @param key
     * @return
     */
    @Override
    public ValueWrapper get(Object key) {
        return null;
    }

    /**
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    @Override
    public <T> T get(Object key, Class<T> type) {
        return null;
    }

    /**
     * @param key
     * @param valueLoader
     * @param <T>
     * @return
     */
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }

    /**
     * @param key
     * @return
     */
    @Override
    public CompletableFuture<?> retrieve(Object key) {
        return Cache.super.retrieve(key);
    }

    /**
     * @param key
     * @param valueLoader
     * @param <T>
     * @return
     */
    @Override
    public <T> CompletableFuture<T> retrieve(Object key, Supplier<CompletableFuture<T>> valueLoader) {
        return Cache.super.retrieve(key, valueLoader);
    }

    /**
     * @param key
     * @param value
     */
    @Override
    public void put(Object key, Object value) {

    }

    /**
     * @param key
     * @param value
     * @return
     */
    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return Cache.super.putIfAbsent(key, value);
    }

    /**
     * @param key
     */
    @Override
    public void evict(Object key) {

    }

    /**
     * @param key
     * @return
     */
    @Override
    public boolean evictIfPresent(Object key) {
        return Cache.super.evictIfPresent(key);
    }

    /**
     *
     */
    @Override
    public void clear() {

    }

    /**
     * @return
     */
    @Override
    public boolean invalidate() {
        return Cache.super.invalidate();
    }
}

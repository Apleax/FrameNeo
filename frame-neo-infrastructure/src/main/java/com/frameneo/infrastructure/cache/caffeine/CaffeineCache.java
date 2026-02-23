package com.frameneo.infrastructure.cache.caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.support.NullValue;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.concurrent.Callable;

/**
 * Caffeine缓存适配器
 */
public class CaffeineCache implements Cache {
    /// Caffeine缓存对象
    private final com.github.benmanes.caffeine.cache.Cache<Object, Object> cache;

    /// 缓存名称
    private final String name;

    /**
     * 基础构造函数
     * 默认最大缓存数量为1000
     * 默认缓存时间为30分
     * @param name 缓存名称
     */
    public CaffeineCache(String name) {
        Assert.notNull(name, "Name must not be null");
        this.cache = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(30, java.util.concurrent.TimeUnit.MINUTES)
                .build();
        this.name = name;
    }

    /**
     * 高级构建函数
     * @param name 缓存名称
     * @param caffeineCache 缓存构对象
     */
    public CaffeineCache(String name, com.github.benmanes.caffeine.cache.Cache<Object, Object> caffeineCache) {
        Assert.notNull(name, "Name must not be null");
        Assert.notNull(caffeineCache, "Cache must not be null");
        this.cache = caffeineCache;
        this.name = name;
    }

    /**
     * 获取缓存名称
     * @return 缓存名称
     */
    @Override
    @NonNull
    public String getName() {
        return this.name;
    }

    /**
     * 获取缓存对象
     * @return 缓存对象
     */
    @Override
    @NonNull
    public Object getNativeCache() {
        return this.cache;
    }

    /**
     * @param key 缓存key
     * @return 缓存对象
     */
    @Override
    public ValueWrapper get(@NonNull Object key) {
        Assert.notNull(key, "Key must not be null");

        Object value = this.cache.getIfPresent(key);

        if (value == null) {
            return null;
        }

        return new SimpleValueWrapper(value == NullValue.INSTANCE ? null : value);
    }

    /**
     * 根据key获取缓存对象
     * @param key 缓存key
     * @param type 缓存对象类型
     * @param <T> 缓存对象类型
     * @return 缓存对象
     */
    @Override
    public <T> T get(@NonNull Object key, Class<T> type) {
        Assert.notNull(key, "Key must not be null");
        Assert.notNull(type, "Type must not be null");

        Object value = this.cache.getIfPresent(key);

        if (value == null || value == NullValue.INSTANCE) {
            return null;
        }

        if (!type.isInstance(value)) {
            throw new IllegalStateException(
                    "Cached value is not of required type [" + type.getName() + "]: " + value
            );
        }

        return type.cast(value);
    }

    /**
     * 根据key获取缓存对象
     * @param key 缓存key
     * @param valueLoader 缓存加载对象
     * @param <T> 缓存对象类型
     * @return 缓存对象
     */
    @Override
    public <T> T get(@NonNull Object key, @NonNull Callable<T> valueLoader) {
        Assert.notNull(key, "Key must not be null");
        Assert.notNull(valueLoader, "ValueLoader must not be null");

        try {
            Object value = this.cache.get(key, k -> {
                try {
                    T loaded = valueLoader.call();
                    return (loaded == null ? NullValue.INSTANCE : loaded);
                } catch (Exception ex) {
                    throw new Cache.ValueRetrievalException(key, valueLoader, ex);
                }
            });

            return (value == NullValue.INSTANCE ? null : (T) value);

        } catch (ClassCastException ex) {
            throw new Cache.ValueRetrievalException(key, valueLoader, ex);
        } catch (RuntimeException ex) {
            if (ex.getCause() instanceof Cache.ValueRetrievalException) {
                throw (Cache.ValueRetrievalException) ex.getCause();
            }
            throw ex;
        }


    }

    /**
     * 缓存对象
     * @param key 缓存key
     * @param value 缓存对象
     */
    @Override
    public void put(@NonNull Object key, Object value) {
        Assert.notNull(key, "Key must not be null");
        cache.put(key, (value == null ? NullValue.INSTANCE : value));
    }

    /**
     * 删除缓存对象
     * @param key 缓存key
     */
    @Override
    public void evict(@NonNull Object key) {
        cache.invalidate(key);
    }

    /**
     * 清空缓存
     */
    @Override
    public void clear() {
        cache.invalidateAll();
    }
}

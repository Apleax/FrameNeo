package com.frameneo.infrastructure.cache.caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.stream.Collectors;

public class CaffeineCacheManger extends AbstractCacheManager {

    private final CaffeineCacheProperties cacheProperties;

    public CaffeineCacheManger(CaffeineCacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    /**
     * Spring Cache 的缓存初始化接口
     * 会在容器启动时调用，返回所有缓存实例
     */
    @Override
    @NonNull
    protected Collection<? extends Cache> loadCaches() {
        return cacheProperties.getCaches().entrySet().stream()
                .map((entry -> {
                    String name = entry.getKey();
                    CaffeineCacheProperties.CaffeineCacheConfig config = entry.getValue();

                    /// 构建原生 Caffeine 缓存
                    com.github.benmanes.caffeine.cache.Cache<Object, Object> cache = Caffeine.newBuilder()
                            .maximumSize(config.getMaxSize())
                            .expireAfterWrite(config.getExpireMinutes(), java.util.concurrent.TimeUnit.MINUTES)
                            .build();
                    return new CaffeineCache(name, cache);
                }))
                .collect(Collectors.toList());
    }
}

package com.frameneo.infrastructure.cache.caffeine;

import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.HashMap;

/**
 * Caffeine缓存配置
 */
@Data
public class CaffeineCacheProperties {

    /**
     * 缓存配置映射,每个缓存名单独配置
     * key: 缓存名
     * value: 缓存配置
     */
    private HashMap<String, CaffeineCacheConfig> caches = new HashMap<>();

    @Data
    public static class CaffeineCacheConfig {
        @Min(1)
        private long maxSize = 1000; /// 最大缓存数量
        @Min(1)
        private long expireMinutes = 30; /// 缓存时间
    }
}
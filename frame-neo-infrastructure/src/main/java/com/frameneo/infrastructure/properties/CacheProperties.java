package com.frameneo.infrastructure.properties;

import com.frameneo.infrastructure.cache.caffeine.CaffeineCacheProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

/**
 * Cache缓存配置
 */
@Data
@ConfigurationProperties(prefix = "cache")
@Validated
public class CacheProperties {
    /// 缓存类型
    @NotBlank(message = "缓存类型cache.type不能为空")
    @NonNull
    private String type = CacheType.CAFFEINE.name();

    /**
     * 缓存类型枚举
     */
    public enum CacheType {
        CAFFEINE, REDIS
    }

    /**
     * Caffeine缓存配置
     */
    @Nullable
    private CaffeineCacheProperties caffeine;
}

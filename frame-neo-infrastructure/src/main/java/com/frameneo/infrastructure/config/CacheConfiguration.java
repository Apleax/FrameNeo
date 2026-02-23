package com.frameneo.infrastructure.config;

import com.frameneo.infrastructure.cache.caffeine.CaffeineCacheManger;
import com.frameneo.infrastructure.properties.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class CacheConfiguration {
    private final CacheProperties cacheProperties;

    public CacheConfiguration(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    @Bean
    @ConditionalOnMissingBean(CacheManager.class)
    @ConditionalOnProperty(name = "cache.type", havingValue = "CAFFEINE", matchIfMissing = true)
    public CacheManager caffeineCacheManager() {
        return new CaffeineCacheManger(cacheProperties.getCaffeine());
    }
}

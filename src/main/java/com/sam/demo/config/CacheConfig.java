package com.sam.demo.config;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author sam
 * @date 11/15/19 9:56
 */
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager("configQuery");
        Caffeine caffeine = Caffeine.newBuilder()
                                    .expireAfterWrite(5, TimeUnit.MINUTES)
                                    .maximumSize(100);
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }

}

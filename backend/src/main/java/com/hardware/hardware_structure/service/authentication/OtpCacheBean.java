package com.hardware.hardware_structure.service.authentication;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.hardware.hardware_structure.core.configuration.Constants;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class OtpCacheBean {
    @Bean
    public LoadingCache<@NonNull String, @NonNull Integer> loadingCache() {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(Constants.OTP_EXPIRATION_MINUTES, TimeUnit.MINUTES)
                .build(new CacheLoader<>() {
                    @Override
                    @NonNull
                    public Integer load(@NonNull String key) {
                        return 0;
                    }
                });
    }
}

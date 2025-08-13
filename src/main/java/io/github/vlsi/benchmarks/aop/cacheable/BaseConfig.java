package io.github.vlsi.benchmarks.aop.cacheable;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseConfig {
    @Bean
    public Calculator calculator() {
        return new PlainCalculator();
    }
}

@Configuration
abstract class CacheManagerConfig extends BaseConfig {
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }
}

@Configuration
@EnableCaching(proxyTargetClass = true)
class CacheableCglibConfig extends CacheManagerConfig {
}

@Configuration
@EnableCaching(proxyTargetClass = false)
class CacheableJdkProxyConfig extends CacheManagerConfig {
}

@Configuration
class ManualCaching extends BaseConfig {
    @Override
    public Calculator calculator() {
        Calculator base = super.calculator();
        return new Calculator() {
            Map<Integer, Integer> cache = new ConcurrentHashMap<>();

            @Override
            public int identity(int x) {
                if (cache.containsKey(x)) {
                    return cache.get(x);
                }
                int value = base.identity(x);
                cache.put(x, value);
                return value;
            }
        };
    }
}

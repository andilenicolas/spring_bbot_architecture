package core.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.yourcompany.yourapp.core.config.CaffeineCacheProperties;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class CaffeineCacheService<K, V> implements CacheService<K, V> {

    private final Cache<K, V> cache;

    public CaffeineCacheService(CaffeineCacheProperties config) {
        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(config.getExpireAfterWriteMinutes(), TimeUnit.MINUTES)
                .maximumSize(config.getMaximumSize())
                .build();
    }

    @Override
    public Optional<V> get(K key) {
        return Optional.ofNullable(cache.getIfPresent(key));
    }

    @Override
    public Optional<V> get(K key, Function<K, V> loader) {
        try {
            return Optional.ofNullable(cache.get(key, loader));
        } catch (Exception e) {
            log.warn("Cache load failed for key: {}", key, e);
            return Optional.empty();
        }
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public void invalidate(K key) {
        cache.invalidate(key);
    }

    @Override
    public void invalidateAll() {
        cache.invalidateAll();
    }
}

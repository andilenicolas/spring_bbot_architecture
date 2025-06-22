package core.cache;

import java.util.Optional;
import java.util.function.Function;

/*
 * <dependency>
  <groupId>com.github.ben-manes.caffeine</groupId>
  <artifactId>caffeine</artifactId>
  <version>3.1.8</version>
</dependency>

Least Frequently / Recently Used

*/

public interface CacheService<K, V> {

    /**
     * Get value from cache, or Optional.empty.
     */
    Optional<V> get(K key);

    /**
     * Get value from cache, or load via loader if missing.
     */
    Optional<V> get(K key, Function<K, V> loader);

    /**
     * Puts value into cache.
     */
    void put(K key, V value);

    /**
     * Invalidates a specific cache key.
     */
    void invalidate(K key);

    /**
     * Invalidates the entire cache.
     */
    void invalidateAll();
}

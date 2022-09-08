package io.pismo.transaction.adapter.out.redis;

import io.pismo.transaction.domain.port.out.RedisCache;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisCacheImpl implements RedisCache {

  private static final Logger logger = LoggerFactory.getLogger(RedisCacheImpl.class);

  private final RedisTemplate<String, String> redisTemplate;

  private ValueOperations<String, String> valueOperations;

  public RedisCacheImpl(RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public void create(String prefix, String key, String value) {
    try {
      this.valueOperations = this.redisTemplate.opsForValue();

      logger.info("Saving... cache with key {}", prefix.concat(key));

      valueOperations.set(prefix.concat(key), value, 1, TimeUnit.HOURS);
    } catch (Exception e) {
      logger.warn("An unexpected error occurred, unable to save cached data");
    }
  }

  public Optional<String> find(String prefix, String key) {
    try {
      this.valueOperations = this.redisTemplate.opsForValue();

      logger.info("Getting... cache with key {}", prefix.concat(key));

      return Optional.ofNullable(valueOperations.get(prefix.concat(key)));
    } catch (Exception e) {
      logger.warn("An unexpected error occurred, unable to get cached data");
    }

    return Optional.empty();
  }
}

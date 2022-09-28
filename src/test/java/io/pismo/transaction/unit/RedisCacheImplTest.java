package io.pismo.transaction.unit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.pismo.transaction.adapter.out.redis.RedisCacheImpl;
import io.pismo.transaction.domain.models.enums.RedisKeyEnum;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@ExtendWith(MockitoExtension.class)
public class RedisCacheImplTest {

  @InjectMocks
  RedisCacheImpl redisCache;

  @Mock
  RedisTemplate<String, String> redisTemplate;

  @Mock
  ValueOperations<String, String> valueOperations;

  @Test
  void should_set_a_new_cache() {
    assertDoesNotThrow(
        () -> this.redisCache.create(RedisKeyEnum.CACHE_ACCOUNT.getValue(), "1", "123"));

    verify(this.redisTemplate, times(1)).opsForValue();
  }

  @Test
  void should_given_an_error_when_redis_template_is_null() {
    when(redisTemplate.opsForValue()).thenReturn(null);

    assertDoesNotThrow(
        () -> this.redisCache.create(RedisKeyEnum.CACHE_ACCOUNT.getValue(), "1", "123"));

    verify(this.redisTemplate, times(1)).opsForValue();
  }

  @Test
  void should_get_a_memory_cache() {
    when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    when(valueOperations.get(any())).thenReturn("John Doe");

    Optional<String> cache = assertDoesNotThrow(
        () -> this.redisCache.find(RedisKeyEnum.CACHE_ACCOUNT.getValue(), "1"));

    assertTrue(cache.isPresent());
    assertEquals("John Doe", cache.get());

    verify(this.redisTemplate, times(1)).opsForValue();
  }

  @Test
  void should_given_empty_cache_when_redis_template_is_null() {
    when(redisTemplate.opsForValue()).thenReturn(null);

    Optional<String> cache = assertDoesNotThrow(
        () -> this.redisCache.find(RedisKeyEnum.CACHE_ACCOUNT.getValue(), "1"));

    assertFalse(cache.isPresent());

    verify(this.redisTemplate, times(1)).opsForValue();
  }

  @Test
  void should_given_error_with_delete_not_exists_cache() {
    when(redisTemplate.delete(anyString())).thenThrow(new RuntimeException("Error"));

    try {
      this.redisCache.delete(RedisKeyEnum.CACHE_ACCOUNT.getValue(), "-1");
    } catch (Exception e) {
      assertEquals("An unexpected error occurred, unable to save cached data", e.getMessage());
    }

    verify(this.redisTemplate, times(1)).delete(anyString());
  }
}
